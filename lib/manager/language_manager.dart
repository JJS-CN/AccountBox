import 'package:get/get.dart';

class LanguageManager extends Translations {
  @override
  Map<String, Map<String, String>> get keys => {
        "zh_CN": {
          'hello': "你好",
          'back': "返回",
          'check': "确定",
          'Language Change': "语言选择",
          'Theme Change': "主题选择",
          'HomePage': "首页",
          'SplashPage': "开屏页",
          'LoginPage': "登录页",
        },
        "en_US": {
          "hello": "hello",
          "back": "back",
          "check": "check",
          "Language Change": "Language Change",
          "Theme Change": "Theme Change",
          'HomePage': "HomePage",
          'SplashPage': "SplashPage",
          'LoginPage': "LoginPage",
        }
      };
}
