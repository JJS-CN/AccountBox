import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/ui/user_sign_in.dart';
import 'package:accountbox/ui/login_page.dart';
import 'package:flutter/material.dart';
import 'package:accountbox/manager/language_manager.dart';
import 'package:accountbox/ui/splash_page.dart';
import 'package:get/get.dart';
import 'dart:ui' as ui;
import 'package:accountbox/routes/app_pages.dart';

void main() {
  runApp(GetMaterialApp(
    translations: LanguageManager(),
    locale: ui.window.locale,
    fallbackLocale: const Locale("zh", "CN"),
    home: SignPageFour(),
    getPages: Routes.pages,
  ));
}
