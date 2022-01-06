import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/ui/user_sign_in.dart';
import 'package:flutter/material.dart';
import 'package:accountbox/manager/language_manager.dart';
import 'package:get/get.dart';
import 'dart:ui' as ui;

void main() {
  runApp(GetMaterialApp(
    translations: LanguageManager(),
    locale: ui.window.locale,
    fallbackLocale: const Locale("zh", "CN"),
    home: SignPageFour(),
    getPages: Routes.pages,
  ));
}
