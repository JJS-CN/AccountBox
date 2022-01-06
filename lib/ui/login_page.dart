import 'package:accountbox/manager/theme_manager.dart';
import 'package:accountbox/widget/input_text_field.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:fluttertoast/fluttertoast.dart';

class LoginPage extends StatefulWidget {
  LoginPage({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() {
    return _LoginPageState();
  }
}

class _LoginPageState extends State<LoginPage> {
  var inputHeight = 40.0;
  var inputLeftEdge = EdgeInsets.fromLTRB(20, 10, 10, 10);
  var _passportController = TextEditingController();
  var _passwordController = TextEditingController();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  Widget build(BuildContext context) {
    Future.delayed(Duration(milliseconds: 0)).then((value) {
      setState(() {
        Get.changeTheme(ThemeManager.pink);
      });
    });
    _passportController.addListener(() {
      setState(() {});
    });
    _passwordController.addListener(() {
      setState(() {});
    });
    return Scaffold(
        appBar: AppBar(
          title: Text("LoginPage".tr),
        ),
        body: Container(
          child: Column(
            children: [
              Form(
                  key: _formKey,
                  child: Column(
                    children: [
                      Container(
                        margin: EdgeInsets.fromLTRB(40, 10, 40, 10),
                        child: TextFormField(
                          //监听输入
                          controller: _passportController,
                          //主文本样式
                          style: TextStyle(fontSize: 14, color: Colors.black),
                          //键盘类型
                          keyboardType: TextInputType.emailAddress,
                          validator: (value) {
                            if (value!.length < 2) {
                              return '用户名至少2位数';
                            }
                          },
                          //输入文本过滤器
                          inputFormatters: [
                            LengthLimitingTextInputFormatter(20),
                            FilteringTextInputFormatter.allow(
                                RegExp(r'^[a-zA-Z0-9@_.]+'))
                          ],
                          //内容样式定义
                          decoration: InputDecoration(
                              hintText: "请输入用户名",
                              label: Text("用户名"),
                              contentPadding: EdgeInsets.symmetric(
                                  vertical: 5.0, horizontal: 10.0),
                              //内边距设置
                              border: OutlineInputBorder(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(44.0))),
                              prefixIcon: IconButton(
                                splashColor: Colors.transparent,
                                highlightColor: Colors.transparent,
                                icon: FaIcon(
                                  FontAwesomeIcons.passport,
                                  size: 15,
                                ),
                                onPressed: () {},
                              ),
                              suffixIcon: _passportController.text.length > 0
                                  ? IconButton(
                                      //去除点击动画效果
                                      splashColor: Colors.transparent,
                                      highlightColor: Colors.transparent,
                                      icon: const FaIcon(
                                        FontAwesomeIcons.trash,
                                        size: 15,
                                      ),
                                      onPressed: () =>
                                          {_passportController.clear()},
                                    )
                                  : null),
                        ),
                      ),
                      Container(

                        margin: EdgeInsets.fromLTRB(40, 10, 40, 10),
                        child: TextFormField(
                          controller: _passwordController,
                          style: const TextStyle(
                              fontSize: 14, color: Colors.black),
                          keyboardType: TextInputType.visiblePassword,
                          inputFormatters: [
                            LengthLimitingTextInputFormatter(20),
                            FilteringTextInputFormatter.allow(
                                RegExp(r'^[a-zA-Z0-9@_.]+'))
                          ],
                          validator: (value) {
                            if (value!.length < 6) {
                              return '密码至少6位数';
                            }
                          },
                          decoration: InputDecoration(
                              hintText: "请输入密码",
                              label: const Text("密码"),
                              contentPadding: const EdgeInsets.symmetric(
                                  vertical: 5.0, horizontal: 10.0),
                              //内边距设置
                              border: const OutlineInputBorder(
                                  borderRadius:
                                      BorderRadius.all(Radius.circular(44.0))),
                              //左侧小图标
                              prefixIcon: IconButton(
                                splashColor: Colors.transparent,
                                highlightColor: Colors.transparent,
                                icon: const FaIcon(
                                  FontAwesomeIcons.key,
                                  size: 15,
                                ), onPressed: () {  },
                              ),
                              suffixIcon: _passwordController.text.length > 0
                                  ? IconButton(
                                      //去除点击动画效果
                                      splashColor: Colors.transparent,
                                      highlightColor: Colors.transparent,
                                      icon: const FaIcon(
                                        FontAwesomeIcons.trash,
                                        size: 15,
                                      ),
                                      onPressed: () =>
                                          {_passwordController.clear()},
                                    )
                                  : null),
                        ),
                      ),
                    ],
                  )),
              Container(
                margin: const EdgeInsets.fromLTRB(40, 10, 40, 10),
                width: double.infinity,
                height: inputHeight,
                child: OutlinedButton(
                    style: ButtonStyle(
                        side: MaterialStateProperty.all(const BorderSide(
                            width: 1, color: Color(0xFFFFF333))),
                        shape: MaterialStateProperty.all(const StadiumBorder(
                            side: BorderSide(
                          style: BorderStyle.solid,
                        )))),
                    child: const Text("登录"),
                    onPressed: () => {
                          _formKey.currentState!.validate(),
                          Fluttertoast.showToast(
                              msg: "This is Center Short Toast",
                              toastLength: Toast.LENGTH_SHORT,
                              gravity: ToastGravity.CENTER,
                              timeInSecForIosWeb: 1,
                              backgroundColor: Colors.red,
                              textColor: Colors.white,
                              fontSize: 16.0)
                        }),
              )
            ],
          ),
        ));
  }
}
