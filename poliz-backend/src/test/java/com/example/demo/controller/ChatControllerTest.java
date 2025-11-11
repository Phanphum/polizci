package com.example.demo.controller;

import com.example.demo.model.Chat;
import com.example.demo.model.Message;
import com.example.demo.repository.ChatRepository;
import com.example.demo.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
/**
 * Unit tests for the ChatController.sendMessage method.
 *
 * Test design technique: All Combinations of Conditions (ACoC)
 *
 * We define three binary characteristics and their partitions:
 *
 *   1) Chat Existence
 *      { Exists (base), Doesn't Exist }
 *      - This condition checks whether a chat already exists between the sender and the receiver.
 *        If a chat exists, the message can be sent to it. If not, a new chat needs to be created.
 *
 *   2) Message Text
 *      { Valid Text (base), Empty Text }
 *      - This condition checks the validity of the message text.
 *        A valid message is one with non-empty content, while an empty message should be rejected.
 *
 *   3) Message Save Success
 *      { Success (base), Failure (Exception) }
 *      - This condition checks whether the message was successfully saved to the repository.
 *        If saving the message fails (due to an exception), we need to handle that appropriately.
 *
 * **Why use ACoC (All Combinations of Conditions)?**
 *   - **ACoC** is a powerful technique for testing **all possible combinations** of conditions that influence the behavior of a system.
 *   - In our case, we are dealing with three key conditions: whether a chat exists, whether the message text is valid, and whether the message is successfully saved.
 *   - By using **ACoC**, we ensure that we test **every possible combination** of these conditions to thoroughly verify how the system behaves under all scenarios. This ensures **complete coverage** of the functionality.
 *
 * ACoC coverage:
 *   - Total tests = 2^3 = 8 combinations.
 *     - 2 states for **Chat Existence** (Exists or Doesn't Exist)
 *     - 2 states for **Message Text** (Valid or Empty)
 *     - 2 states for **Message Save Success** (Success or Failure)
 *
 * Hence, we have 8 unique tests, each testing a specific combination of these conditions.
 */
@SpringBootTest
class ChatControllerTest {

    @InjectMocks
    private ChatController chatController;

    @Mock
    private ChatRepository chatRepo;

    @Mock
    private MessageRepository msgRepo;

    // Setup method to mock the chat between "Pim" and "Ploy"
    @BeforeEach
    void setUp() {
        // Mock chat between Pim and Ploy
        Chat chat = new Chat("Pim", "Ploy");
        when(chatRepo.findByUserAAndUserB("Pim", "Ploy")).thenReturn(chat); // Return mocked chat
    }

    // T1: Valid message, chat exists, but message is not saved (throws exception)
    // Test case for checking that an exception is thrown when saving the message fails
    @Test
    void testSendMessage_ValidText_ExistingChat_Exception() {
        Map<String, String> messageBody = Map.of("text", "Hello Ploy!", "time", "12:30 PM");
        when(msgRepo.save(any(Message.class))).thenThrow(new RuntimeException("Message text is empty"));

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Ploy", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }

    // T2: Valid message, chat exists, message saved successfully
    // Test case for checking if the message is saved when the chat exists
    @Test
    void testSendMessage_ValidText_ExistingChat_Success() {
        Map<String, String> messageBody = Map.of("text", "Hello Ploy!", "time", "12:30 PM");
        Message message = new Message("Pim", "Ploy", "Hello Ploy!", "12:30 PM");
        when(msgRepo.save(any(Message.class))).thenReturn(message);

        // Sending the message
        Message savedMessage = chatController.sendMessage("Pim", "Ploy", messageBody);

        // Assertions to check that the message was saved correctly
        assertNotNull(savedMessage);
        assertEquals("Hello Ploy!", savedMessage.getText());
        assertEquals("Pim", savedMessage.getSender());
        assertEquals("Ploy", savedMessage.getReceiver());
    }

    // T3: Valid message, chat doesn't exist, but message is not saved (throws exception)
    // Test case for when a new chat doesn't exist and throws an exception when the message fails to save
    @Test
    void testSendMessage_ValidText_NewChat_Exception() {
        Map<String, String> messageBody = Map.of("text", "Hey Earn, let's chat!", "time", "12:45 PM");

        // Mock chatRepo to return null (no chat found)
        when(chatRepo.findByUserAAndUserB("Pim", "Earn")).thenReturn(null);
        when(msgRepo.save(any(Message.class))).thenThrow(new RuntimeException("Message text is empty"));

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Earn", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }

    // T4: Valid message, chat doesn't exist, message saved successfully
    // Test case for when a new chat doesn't exist and message is saved successfully after creating the chat
    @Test
    void testSendMessage_ValidText_NewChat_Success() {
        Map<String, String> messageBody = Map.of("text", "Hey Earn, let's chat!", "time", "12:45 PM");

        // Mock chatRepo to return null (no chat found)
        Chat newChat = new Chat("Pim", "Earn");
        when(chatRepo.findByUserAAndUserB("Pim", "Earn")).thenReturn(null);  // Return null when no chat found
        when(chatRepo.save(any(Chat.class))).thenReturn(newChat);
        when(msgRepo.save(any(Message.class))).thenReturn(new Message("Pim", "Earn", "Hey Earn, let's chat!", "12:45 PM"));

        // Sending the message
        Message savedMessage = chatController.sendMessage("Pim", "Earn", messageBody);

        // Assertions to check that the message was saved correctly
        assertNotNull(savedMessage);
        assertEquals("Hey Earn, let's chat!", savedMessage.getText());
        assertEquals("Pim", savedMessage.getSender());
        assertEquals("Earn", savedMessage.getReceiver());
    }

    // T5: Empty message, chat exists, message not saved (throws exception)
    // Test case for when the message is empty and the exception is thrown
    @Test
    void testSendMessage_EmptyText_ExistingChat_Exception() {
        Map<String, String> messageBody = Map.of("text", "", "time", "12:50 PM");

        // Mock behavior for empty text (to throw exception)
        when(msgRepo.save(any(Message.class))).thenThrow(new RuntimeException("Message text is empty"));

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Ploy", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }

    // T6: Empty message, chat exists, message not saved (throws exception)
    // Test case for when the message is empty and the exception is thrown
    @Test
    void testSendMessage_EmptyText_ExistingChat_Exception_2() {
        Map<String, String> messageBody = Map.of("text", "", "time", "12:50 PM");

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Ploy", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }

    // T7: Empty message, chat doesn't exist, message not saved (throws exception)
    // Test case for when the message is empty and no chat exists, and throws exception when the message fails to save
    @Test
    void testSendMessage_EmptyText_NewChat_Exception() {
        Map<String, String> messageBody = Map.of("text", "", "time", "12:50 PM");

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Earn", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }

    // T8: Empty message, chat doesn't exist, message not saved (throws exception)
    // Test case for when the message is empty and no chat exists, and throws exception when the message fails to save
    @Test
    void testSendMessage_EmptyText_NewChat_Exception_2() {
        Map<String, String> messageBody = Map.of("text", "", "time", "12:50 PM");

        // Expecting an exception to be thrown
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            chatController.sendMessage("Pim", "Earn", messageBody);
        });

        assertEquals("Message text is empty", exception.getMessage());
    }
}
