// lib/styles/app_theme.dart
import 'package:flutter/material.dart';

class AppColors {
  // brand
  static const slate900 = Color(0xFF0F172A);
  static const slate950 = Color(0xFF0B1221);
  static const surfaceDark = slate900;
  static const backgroundDark = slate950;

  // accents
  static const policeBlue = Color(0xFF2563EB);
  static const sky = Color(0xFF38BDF8);
  static const cyan = Color(0xFF22D3EE);

  // neutrals
  static const textPrimary = Colors.white;
  static const textSecondary = Colors.white70;
  static const divider = Color(0x1FFFFFFF);
  static const stroke = Color(0xFF1F2937);

  // semantic
  static const warn = Color(0xFFFFB02E);
  static const danger = Color(0xFFE11D48);
  static const success = Color(0xFF22C55E);
}

class AppRadii {
  static const lg = Radius.circular(20);
  static const pill = Radius.circular(999);
}

class AppSpace {
  static const x = 8.0;
  static const m = 12.0;
  static const l = 16.0;
  static const xl = 20.0;
}

class AppShadows {
  static const soft = [
    BoxShadow(
      color: Colors.black38,
      blurRadius: 14,
      spreadRadius: -10,
      offset: Offset(0, 14),
    ),
  ];
}

TextTheme _textThemeDark = const TextTheme(
  headlineSmall: TextStyle(fontWeight: FontWeight.w800, color: AppColors.textPrimary),
  titleLarge: TextStyle(fontWeight: FontWeight.w700, color: AppColors.textPrimary),
  titleMedium: TextStyle(fontWeight: FontWeight.w700, color: AppColors.textPrimary),
  bodyLarge: TextStyle(color: AppColors.textPrimary),
  bodyMedium: TextStyle(color: AppColors.textSecondary),
);

InputDecorationTheme _inputTheme = const InputDecorationTheme(
  isDense: true,
  filled: false,
  hintStyle: TextStyle(color: AppColors.textSecondary),
  border: OutlineInputBorder(
    borderRadius: BorderRadius.all(Radius.circular(14)),
  ),
  enabledBorder: OutlineInputBorder(
    borderSide: BorderSide(color: AppColors.stroke),
    borderRadius: BorderRadius.all(Radius.circular(14)),
  ),
  focusedBorder: OutlineInputBorder(
    borderSide: BorderSide(color: AppColors.sky, width: 1.4),
    borderRadius: BorderRadius.all(Radius.circular(14)),
  ),
);

ButtonStyle _filledBlue = FilledButton.styleFrom(
  backgroundColor: AppColors.policeBlue,
  foregroundColor: Colors.white,
  shape: const StadiumBorder(),
  padding: const EdgeInsets.symmetric(horizontal: 18, vertical: 14),
);

ThemeData buildDarkTheme() {
  final base = ThemeData(
    brightness: Brightness.dark,
    useMaterial3: true,
    colorSchemeSeed: AppColors.slate900,
    scaffoldBackgroundColor: AppColors.backgroundDark,
  );

  return base.copyWith(
    textTheme: _textThemeDark,
    appBarTheme: const AppBarTheme(
      backgroundColor: Colors.transparent,
      elevation: 0,
      titleTextStyle: TextStyle(
        fontWeight: FontWeight.w700,
        fontSize: 20,
        color: AppColors.textPrimary,
      ),
      iconTheme: IconThemeData(color: AppColors.textPrimary),
    ),
    cardTheme: CardThemeData(
      color: AppColors.surfaceDark,
      elevation: 1,
      shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(20)),
      margin: EdgeInsets.zero,
      shadowColor: Colors.black54,
    ),
    dividerColor: AppColors.divider,
    inputDecorationTheme: _inputTheme,
    chipTheme: ChipThemeData(
      backgroundColor: const Color(0xFFF1F5F9).withOpacity(.06),
      side: const BorderSide(color: AppColors.stroke),
      labelStyle: const TextStyle(color: AppColors.textPrimary),
      padding: const EdgeInsets.symmetric(horizontal: 6, vertical: 0),
    ),
    filledButtonTheme: FilledButtonThemeData(style: _filledBlue),
    iconButtonTheme: IconButtonThemeData(
      style: IconButton.styleFrom(foregroundColor: AppColors.textPrimary),
    ),
    listTileTheme: const ListTileThemeData(
      iconColor: AppColors.textSecondary,
      textColor: AppColors.textPrimary,
      dense: true,
    ),
    scrollbarTheme: const ScrollbarThemeData(
      thumbVisibility: MaterialStatePropertyAll(true),
    ),
  );
}