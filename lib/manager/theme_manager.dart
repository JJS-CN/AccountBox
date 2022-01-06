import 'package:flutter/material.dart';

class ThemeManager {
  static final pink = ThemeData(
      //主题色
      primarySwatch: Colors.brown,
      //scaffold背景颜色
      scaffoldBackgroundColor: Colors.white,
      textSelectionTheme: TextSelectionThemeData(
        //光标颜色
        cursorColor: Colors.pink,
        //选中文本背景色
        //selectionColor: Colors.purple,
      ),
      appBarTheme: const AppBarTheme(
          //appbar背景颜色
          backgroundColor: Colors.lightBlue,
          titleTextStyle: TextStyle(fontSize: 16)));
}
