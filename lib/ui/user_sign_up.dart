import 'package:accountbox/const/app_const.dart';
import 'package:accountbox/controller/user_controller.dart';
import 'package:accountbox/model/user_provider.dart';
import 'package:accountbox/model/user_repository.dart';
import 'package:accountbox/routes/app_routes.dart';
import 'package:flutter/material.dart';
import 'package:accountbox/const/gradient_const.dart';
import 'package:accountbox/const/styles.dart';
import 'package:accountbox/widget/signup_apbar.dart';
import 'package:accountbox/ui/user_sign_in.dart';
import 'package:get/get.dart';

class SignPageTeen extends StatefulWidget {
  @override
  _SignPageTeenState createState() => _SignPageTeenState();
}

class _SignPageTeenState extends State<SignPageTeen>
    with TickerProviderStateMixin {
  TextEditingController _username = TextEditingController();
  TextEditingController _password = TextEditingController();
  TextEditingController _password2 = TextEditingController();
  FocusNode _usernameFocusNode = FocusNode();
  FocusNode _passwordFocusNode = FocusNode();
  FocusNode _passwordFocusNode2 = FocusNode();
  final GlobalKey<FormState> _formKey = GlobalKey<FormState>();

  @override
  void dispose() {
    super.dispose();
    //类似于Android的onDestroy
    _usernameFocusNode.unfocus();
    _passwordFocusNode.unfocus();
    _passwordFocusNode2.unfocus();
  }

  @override
  void initState() {
    super.initState();
    //类似onCreate
    _username.addListener(() {
      setState(() {});
    });
    _password.addListener(() {
      setState(() {});
    });
    _password2.addListener(() {
      setState(() {});
    });
    _usernameFocusNode.addListener(() {
      setState(() {});
    });
    _passwordFocusNode.addListener(() {
      setState(() {});
    });
    _passwordFocusNode2.addListener(() {
      setState(() {});
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      resizeToAvoidBottomInset: false,
      appBar: SignupApbar(
        title: "CREATE ACCOUNT",
      ),
      body: Container(
        height: MediaQuery.of(context).size.height,
        width: MediaQuery.of(context).size.width,
        child: Stack(
          alignment: Alignment.centerLeft,
          fit: StackFit.loose,
          children: <Widget>[
            Container(
              height: double.infinity,
              width: double.infinity,
              decoration: BoxDecoration(
                gradient: SIGNUP_BACKGROUND,
              ),
            ),
            buildFirsPage(context)
          ],
        ),
      ),
    );
  }

  Container buildFirsPage(BuildContext context) {
    final _media = MediaQuery.of(context).size;
    return Container(
      height: double.infinity,
      width: double.infinity,
      child: Column(
        children: <Widget>[
          Expanded(
            flex: 1,
            child: Container(
              color: Colors.transparent,
            ),
          ),
          Expanded(
            flex: 7,
            child: Container(
              child: Column(
                children: <Widget>[
                  Text(
                    "ACCOUNT",
                    style: TextStyle(
                      fontSize: 30,
                      fontWeight: FontWeight.bold,
                      letterSpacing: 2,
                      fontFamily: 'Montserrat',
                    ),
                  ),
                  SizedBox(
                    height: 20,
                  ),
                  Container(
                    margin: EdgeInsets.fromLTRB(40, 10, 40, 10),
                    height: _media.height / 2.8,
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
                              child: inputText("USERNAME", 'xxxx@gmail.com',
                                  _username, _usernameFocusNode, (value) {
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
                                child: inputText("PASSWORD", '******',
                                    _password, _passwordFocusNode, (value) {
                              if (value!.length < 6) {
                                return '密码至少6位数';
                              }
                            }, true)),
                            Divider(
                              height: 5,
                              color: Colors.black,
                            ),
                            Expanded(
                                child: inputText("PASSWORD AGAIN", '******',
                                    _password2, _passwordFocusNode2, (value) {
                              if (value!.length < 6) {
                                return '密码至少6位数';
                              }
                              if (_password.text != _password2.text) {
                                return '两次密码不同';
                              }
                            }, true)),
                          ],
                        ),
                      ),
                    ),
                  ),
                  SizedBox(
                    height: 40,
                  ),
                  buildNextButton(),
                  Expanded(
                    flex: 1,
                    child: Container(
                      color: Colors.transparent,
                    ),
                  ),
                  Container(
                    margin: EdgeInsets.fromLTRB(0, 0, 0, 20),
                    child: InkWell(
                      onTap: () => print("Term tapped"),
                      child: Text(
                        "General Terms and Conditions",
                        style: TextStyle(
                          color: Colors.grey.shade600,
                        ),
                      ),
                    ),
                  )
                ],
              ),
            ),
          ),
        ],
      ),
    );
  }

  Padding buildNextButton() {
    return Padding(
      padding: const EdgeInsets.only(bottom: 30.0),
      child: InkWell(
        onTap: () {
          if (_formKey.currentState!.validate()) {
            //执行注册接口
            var provider = UserProvider();
            provider.onInit();
            provider.register(_username.text, _password.text).then((value) =>
                {AppConst.user = value.data!, Get.offAllNamed(Routes.AppHome)});
          }
        },
        child: Container(
          padding: EdgeInsets.symmetric(horizontal: 36.0, vertical: 16.0),
          decoration: BoxDecoration(
              boxShadow: [
                BoxShadow(
                    color: Colors.black12,
                    blurRadius: 15,
                    spreadRadius: 0,
                    offset: Offset(0.0, 32.0)),
              ],
              borderRadius: new BorderRadius.circular(36.0),
              gradient: LinearGradient(begin: FractionalOffset.centerLeft,
// Add one stop for each color. Stops should increase from 0 to 1
                  stops: [
                    0.2,
                    1
                  ], colors: [
                Color(0xff000000),
                Color(0xff434343),
              ])),
          child: Text(
            'Register',
            style: TextStyle(
                color: Color(0xffF1EA94),
                fontWeight: FontWeight.bold,
                fontFamily: 'Montserrat'),
          ),
        ),
      ),
    );
  }

  TextField textField(String labelText, bool obscureText) {
    return TextField(
      style: hintAndValueStyle,
      keyboardAppearance: Brightness.light,
      obscureText: obscureText,
      decoration: new InputDecoration(
        labelText: labelText,
        labelStyle: TextStyle(
          color: Color(0xff353535),
          fontWeight: FontWeight.w500,
          fontSize: 12.0,
        ),
      ),
    );
  }
}
