import 'package:flutter/material.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:integration_test/integration_test.dart';
import 'package:poliz/main.dart' as app;

void main(){
  IntegrationTestWidgetsFlutterBinding.ensureInitialized();

  Future<void> loginAndGoToIncidentImportantRanking(WidgetTester tester) async {
    app.main();
    await tester.pumpAndSettle();
    await tester.enterText(find.byKey(const ValueKey('usernameField')), 'Earn');
    await tester.enterText(find.byKey(const ValueKey('passwordField')), 'Earn');
    await tester.tap(find.byKey(const ValueKey('loginButton')));
    await tester.pumpAndSettle();

    await tester.tap(find.byKey(const ValueKey('AIRankingCard')));
    await tester.pumpAndSettle();
  }

  testWidgets('TC_INCIDENT_ADD_01', (WidgetTester tester) async {
    await loginAndGoToIncidentImportantRanking(tester);

    // Select Incident Type
    await tester.tap(find.byKey(const ValueKey('typeDropdown')));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Fire').last);
    await tester.pumpAndSettle();

    // Enter Place
    await tester.enterText(find.byKey(const ValueKey('placeField')), 'ICT, Mahidol University');
    await tester.enterText(find.byKey(const ValueKey('latitudeField')), '13.7563');
    await tester.enterText(find.byKey(const ValueKey('longtitudeField')), '100.5018');

    // Enter note
    await tester.enterText(find.byKey(const ValueKey('notesField')), 'Smoke coming from 3rd floor, possible fire.');

    // Submit
    await tester.tap(find.byKey(const ValueKey('submitIncident')));
    await tester.pumpAndSettle(const Duration(seconds: 2));

    // EXPECTATION:
    // The new incident appears in the list
    expect(find.text('Fire'), findsWidgets);
    expect(find.text('ICT, Mahidol University'), findsOneWidget);
    expect(find.textContaining('Smoke coming from 3rd floor'), findsOneWidget);
  });

  testWidgets('TC_INCIDENT_ADD_02', (WidgetTester tester) async {

    // Select Incident Type
    await tester.tap(find.byKey(const ValueKey('typeDropdown')));
    await tester.pumpAndSettle();
    await tester.tap(find.text('Fire').last);
    await tester.pumpAndSettle();

    // Enter note
    await tester.enterText(find.byKey(const ValueKey('notesField')), 'Smoke coming from 3rd floor, possible fire.');

    // Submit
    await tester.tap(find.byKey(const ValueKey('submitIncident')));
    await tester.pumpAndSettle(const Duration(seconds: 2));

    // EXPECTATION:
    // The new incident appears in the list
    expect(
        find.widgetWithText(SnackBar, 'Place is required'), 
        findsOneWidget,
        reason: 'Should find the SnackBar with the validation message.'
    );

  });
}