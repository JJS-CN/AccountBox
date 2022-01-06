import 'package:accountbox/const/app_const.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';

class DialogManager {
  static TextEditingController _salt = TextEditingController();
  static GlobalKey<FormState> _saltKey = GlobalKey<FormState>();

  static showSaltInputDialog(Function(bool isAddSuccess) callBack) {
    Get.defaultDialog(
        title: "请输入加盐码",
        content: Container(
          width: 250,
          padding: EdgeInsets.fromLTRB(10, 0, 10, 0),
          child: Column(
            children: [
              Text(
                "输入与登录密码不同的加盐码，用于对后续存储的数据进行加密;\n"
                "存储的账号数据将进行base64转码，再随机插入加盐码后上传至服务器;\n"
                    "将默认使用每次输入的加盐码对所有数据进行解密，所以最好保证加盐码统一。当然-只要记得住可以随意设置",
                style: TextStyle(fontSize: 11, color: Colors.grey),
              ),
              Text(
                "请牢记加盐码，忘记的话加密保存的数据将无法解密转码，因为服务器并不保存你的加盐码",
                style: TextStyle(fontSize: 11, color: Colors.red),
              ),
              Form(
                  key: _saltKey,
                  child: _buildInputText("SALT", 'xxxx', _salt, null, (value) {
                    if (value!.length < 3) {
                      return '加盐码至少需要3位数';
                    }
                  }, false))
            ],
          ),
        ),
        textConfirm: "确定",
        onConfirm: () {
          if (_saltKey.currentState?.validate() ?? false) {
            AppConst.salt = _salt.text;
            _salt.text = "";
            Get.back();
            callBack(false);
          }
        },
        textCancel: "取消");
  }

  static Widget _buildInputText(
    String fieldName,
    String hintText,
    TextEditingController controller,
    FocusNode? focusNode,
    FormFieldValidator<String>? validator,
    bool obSecure,
  ) {
    IconData deleteIcon = FontAwesomeIcons.times;
    return TextFormField(
      focusNode: focusNode,
      style: TextStyle(fontSize: 13),
      controller: controller,
      validator: validator,
      decoration: InputDecoration(
        hintText: hintText,
        hintStyle: TextStyle(color: Colors.grey),
        labelText: fieldName,
        labelStyle: TextStyle(
          fontSize: 12,
          fontFamily: "Montserrat",
          letterSpacing: 0.4,
          height: 0.5,
        ),
        suffixIcon: controller.text.length > 0 && (focusNode?.hasFocus ?? false)
            ? IconButton(
                //去除点击动画效果
                splashColor: Colors.transparent,
                highlightColor: Colors.transparent,
                icon: FaIcon(
                  deleteIcon,
                  size: 15,
                ),
                onPressed: () => {controller.clear()},
              )
            : null,
        focusedBorder: UnderlineInputBorder(
            borderSide: BorderSide(color: Colors.lightBlue, width: 1)),
      ),
      obscureText: obSecure,
    );
  }
}
