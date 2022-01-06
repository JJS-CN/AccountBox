import 'package:accountbox/controller/user_controller.dart';
import 'package:accountbox/model/user_provider.dart';
import 'package:accountbox/model/user_repository.dart';
import 'package:get/get.dart';

class UserBinding implements Bindings {
  @override
  void dependencies() {
    Get.lazyPut<IUserProvider>(() => UserProvider());
    Get.lazyPut<IUserRepository>(
        () => UserRepository(userProvider: Get.find()));
    Get.lazyPut(() => UserController(userRepository: Get.find()));

  }
}
