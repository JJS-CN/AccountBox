import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

InputTextFromField(
    {required String label,//标题
    required String hint,//提示
    required List<TextInputFormatter> format,
    required FaIcon preIcon}) {
  return TextFormField(
    inputFormatters: format,
    decoration: InputDecoration(
      hintText: hint,
      label: Text(label),
      contentPadding:
          const EdgeInsets.symmetric(vertical: 5.0, horizontal: 10.0),
      //内边距设置
      border: const OutlineInputBorder(
          borderRadius: BorderRadius.all(Radius.circular(44.0))),
      prefixIcon: Container(
        padding: const EdgeInsets.fromLTRB(20, 15, 10, 15),
        child: preIcon,
      ),
    ),
  );
}
