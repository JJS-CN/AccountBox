import 'package:accountbox/bean/base_response.dart';
import 'package:accountbox/bean/entitys.dart';
import 'package:accountbox/const/app_const.dart';
import 'package:accountbox/model/base_provider.dart';
import 'package:get/get.dart';
import 'package:dio/dio.dart' hide Response, FormData;
import 'package:fluttertoast/fluttertoast.dart';
abstract class IUserProvider {
  Future<BaseResponse> register(String passport, String password);

  Future<UserEntity?> login(String passport, String password);
}

class UserProvider extends BaseProvider implements IUserProvider {
  @override
  Future<BaseResponse<UserEntity>> register(
      String passport, String password) async {
    //var response = await get("/sss");
    Map<String, dynamic> map = {};
    map['passport'] = passport;
    map['password'] = password;
    FormData data = FormData(map);
    Response response = await post('/user/sign-up', data);
    print(response.body);
    var body = BaseResponse<UserEntity>.fromJson(
        response.body, (json) => UserEntity.fromJson(json));
    Fluttertoast.showToast(
      msg: body.message,
    );
    return body;
  }

  @override
  Future<UserEntity?> login(String passport, String password) async {
    Map<String, dynamic> map = {};
    map['passport'] = passport;
    map['password'] = password;
    FormData data = FormData(map);
    Response response = await post('/user/sign-in', data);
    print(response.body);
    var body = BaseResponse<UserEntity>.fromJson(
        response.body, (json) => UserEntity.fromJson(json));
    Fluttertoast.showToast(
      msg: body.message,
    );
    if (body.success) {
      AppConst.user = body.data!;
      return body.data!;
    } else {
      return null;
    }
  }
}
