import 'package:accountbox/model/user_provider.dart';
import 'package:accountbox/routes/app_routes.dart';
import 'package:accountbox/ui/SignPageSix.dart';
import 'package:accountbox/ui/user_sign_up.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:accountbox/const/color_const.dart';
import 'package:accountbox/const/gradient_const.dart';
import 'package:accountbox/const/images_const.dart';
import 'package:accountbox/const/size_const.dart';
import 'package:accountbox/const/string_const.dart';
import 'package:accountbox/widget/signup_arrow_button.dart';
import 'package:get/get.dart';
import 'package:font_awesome_flutter/font_awesome_flutter.dart';
import 'package:font_awesome_flutter/src/fa_duotone_icon.dart';

class SignPageFour extends StatefulWidget {
  @override
  _SignPageFourState createState() => _SignPageFourState();
}

class _SignPageFourState extends State<SignPageFour> {
  TextEditingController _username = TextEditingController();
  TextEditingController _password = TextEditingController();
  FocusNode _usernameFocusNode = FocusNode();
  FocusNode _passwordFocusNode = FocusNode();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();
  UserProvider _userProvider = UserProvider();

  @override
  void dispose() {
    super.dispose();
    //类似于Android的onDestroy
    _usernameFocusNode.unfocus();
    _passwordFocusNode.unfocus();
  }

  @override
  void initState() {
    super.initState();
    _userProvider.onInit();
    //类似onCreate
    _username.addListener(() {
      setState(() {});
    });
    _password.addListener(() {
      setState(() {});
    });
    _usernameFocusNode.addListener(() {
      setState(() {});
    });
    _passwordFocusNode.addListener(() {
      setState(() {});
    });
    if (!kReleaseMode) {
      _username.text = "jsji";
      _password.text = "123456";
    }
  }

  @override
  void deactivate() {
    super.deactivate();
    //类似onStop,页面移除的时候触发
  }

  @override
  Widget build(BuildContext context) {
    final _media = MediaQuery.of(context).size;

    return Scaffold(
      body: Container(
        height: double.infinity,
        width: double.infinity,
        decoration: BoxDecoration(
          gradient: SIGNUP_BACKGROUND,
        ),
        child: SingleChildScrollView(
          physics: BouncingScrollPhysics(),
          child: Stack(
            children: <Widget>[
              Column(
                children: <Widget>[
                  Padding(
                    padding: const EdgeInsets.symmetric(
                        vertical: 60.0, horizontal: 40),
                    child: Column(
                      crossAxisAlignment: CrossAxisAlignment.start,
                      children: <Widget>[
                        Center(
                          child: Image.asset(
                            SignUpImagePath.SignUpLogo,
                            height: _media.height / 7,
                          ),
                        ),
                        SizedBox(
                          height: 30,
                        ),
                        Text(
                          "WELCOME BACK!",
                          style: TextStyle(
                            letterSpacing: 4,
                            fontFamily: "Montserrat",
                            fontWeight: FontWeight.bold,
                            fontSize: TEXT_LARGE_SIZE,
                          ),
                        ),
                        SizedBox(height: 30),
                        Text(
                          'Log in',
                          style: TextStyle(
                              fontFamily: "Montserrat",
                              fontWeight: FontWeight.w200,
                              fontSize: 40),
                        ),
                        Text(
                          'to continue.',
                          style: TextStyle(
                              fontFamily: "Montserrat",
                              fontWeight: FontWeight.w200,
                              fontSize: 40),
                        ),
                        SizedBox(
                          height: 50,
                        ),
                        Container(
                          height: _media.height / 3.8,
                          decoration: BoxDecoration(
                            gradient: SIGNUP_CARD_BACKGROUND,
                            borderRadius: BorderRadius.circular(15),
                            boxShadow: [
                              BoxShadow(
                                color: Colors.black12,
                                blurRadius: 15,
                                spreadRadius: 8,
                              ),
                            ],
                          ),
                          child: Padding(
                            padding: const EdgeInsets.all(30.0),
                            child: Form(
                              key: _formKey,
                              child: Column(
                                children: <Widget>[
                                  Expanded(
                                    child: inputText(
                                        "USERNAME",
                                        'xxxx@gmail.com',
                                        _username,
                                        _usernameFocusNode, (value) {
                                      if (value!.length < 2) {
                                        return '用户名至少2位数';
                                      }
                                    }, false),
                                  ),
                                  Divider(
                                    height: 5,
                                    color: Colors.black,
                                  ),
                                  Expanded(
                                      child: inputText(
                                          "PASSWORD",
                                          '******',
                                          _password,
                                          _passwordFocusNode, (value) {
                                    if (value!.length < 6) {
                                      return '密码至少6位数';
                                    }
                                  }, true)),
                                ],
                              ),
                            ),
                          ),
                        ),
                      ],
                    ),
                  ),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    crossAxisAlignment: CrossAxisAlignment.center,
                    children: <Widget>[
                      Text(
                        StringConst.SIGN_UP_TEXT,
                        style: TextStyle(color: MAIN_COLOR),
                      ),
                      SizedBox(
                        width: 5,
                      ),
                      GestureDetector(
                        onTap: () => {
                          //清除软键盘
                          FocusScope.of(context).requestFocus(FocusNode()),
                          //跳转注册页面
                          Get.toNamed(Routes.UserRegister)
                        },
                        child: Text(StringConst.SIGN_UP),
                      ),
                    ],
                  ),
                  SizedBox(
                    height: 50,
                  )
                ],
              ),
              Positioned(
                bottom: _media.height / 6.3,
                right: 15,
                child: SignUpArrowButton(
                  icon: IconData(0xe901, fontFamily: 'Icons'),
                  iconSize: 9,
                  onTap: () => {
                    if (_formKey.currentState!.validate())
                      {
                        //执行登录接口
                        _userProvider
                            .login(_username.text, _password.text)
                            .then((value) => {
                                  if (value != null)
                                    {Get.offAllNamed(Routes.AppHome)}
                                })
                      },
                  },
                ),
              ),
            ],
          ),
        ),
      ),
    );
  }
}

Widget inputText(
  String fieldName,
  String hintText,
  TextEditingController controller,
  FocusNode focusNode,
  FormFieldValidator<String>? validator,
  bool obSecure,
) {
  IconData deleteIcon = FontAwesomeIcons.times;
  return TextFormField(
    focusNode: focusNode,
    style: TextStyle(height: 1.3),
    controller: controller,
    validator: validator,
    decoration: InputDecoration(
      hintText: hintText,
      hintStyle: TextStyle(color: Colors.grey),
      labelText: fieldName,
      labelStyle: TextStyle(
        fontSize: TEXT_NORMAL_SIZE,
        fontFamily: "Montserrat",
        fontWeight: FontWeight.w400,
        letterSpacing: 1,
        height: 0,
      ),
      suffixIcon: controller.text.length > 0 && focusNode.hasFocus
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
      border: InputBorder.none,
    ),
    obscureText: obSecure,
  );
}
