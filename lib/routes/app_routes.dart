import 'package:accountbox/model/bindings.dart';
import 'package:accountbox/ui/home_page.dart';
import 'package:accountbox/ui/setting_page.dart';
import 'package:accountbox/ui/user_sign_in.dart';
import 'package:accountbox/ui/user_sign_up.dart';
import 'package:get/get.dart';

class Routes {
  static String UserRegister = "/user/register";
  static String UserLogin = "/user/login";
  static String AppHome = "/app/home";
  static String AppSetting = "/app/setting";

  //定义路由组
  static final pages = [
    GetPage(
        name: Routes.UserLogin,
        binding: UserBinding(),
        page: () => SignPageFour()),
    GetPage(
        name: Routes.UserRegister,
        binding: UserBinding(),
        page: () => SignPageTeen()),
    GetPage(
      name: Routes.AppHome,
      page: () => HomePage(),
    ),
    GetPage(
      name: Routes.AppSetting,
      page: () => SettingPage(),
    ),
  ];
}
