import 'package:accountbox/bean/base_response.dart';
import 'package:accountbox/bean/entitys.dart';
import 'package:accountbox/const/app_const.dart';
import 'package:accountbox/model/base_provider.dart';
import 'package:get/get.dart';
import 'package:dio/dio.dart' hide Response, FormData;
import 'package:fluttertoast/fluttertoast.dart';

abstract class IAccountProvider {
  Future<BaseResponse?> addGroup(String groupName);

  Future<BaseResponse?> deleteGroup(int groupId);

  Future<BaseResponse?> addAccount(int groupId, String accountName,
      String passport, String password, String remarks,bool isEncryption);

  Future<BaseResponse?> deleteAccount(int accountId);

  Future<List<GroupEntity>?> getGroups(bool needAccounts);
}

class AccountProvider extends BaseProvider implements IAccountProvider {
  @override
  Future<BaseResponse?> addAccount(int groupId, String accountName,
      String passport, String password, String remarks,bool isEncryption) async {
    Map<String, dynamic> map = {};
    map['userId'] = AppConst.user.userId;
    map['groupId'] = groupId;
    map['accountName'] = accountName;
    map['passport'] = passport;
    map['password'] = password;
    map['remarks'] = remarks;
    map['remarks'] = remarks;
    map['isEncryption'] = isEncryption;
    FormData data = FormData(map);
    Response response = await post('/account/addAccount', data);
    print(response.body);
    var body = BaseResponse.fromJson(response.body, (json) => {});

    if (body.success) {
      return body;
    } else {
      Fluttertoast.showToast(
        msg: body.message,
      );
      return null;
    }
  }

  @override
  Future<BaseResponse?> addGroup(String groupName) async {
    Map<String, dynamic> map = {};
    map['userId'] = AppConst.user.userId;
    map['groupName'] = groupName;
    FormData data = FormData(map);
    Response response = await post('/account/addGroup', data);
    print(response.body);
    var body = BaseResponse.fromJson(response.body, (json) => {});

    if (body.success) {
      return body;
    } else {
      Fluttertoast.showToast(
        msg: body.message,
      );
      return null;
    }
  }

  @override
  Future<BaseResponse?> deleteAccount(int accountId) async {
    Map<String, dynamic> map = {};
    map['userId'] = AppConst.user.userId;
    map['accountId'] = accountId;
    FormData data = FormData(map);
    Response response = await post('/account/delAccount', data);
    print(response.body);
    var body = BaseResponse.fromJson(response.body, (json) => {});

    if (body.success) {
      return body;
    } else {
      Fluttertoast.showToast(
        msg: body.message,
      );
      return null;
    }
  }

  @override
  Future<BaseResponse?> deleteGroup(int groupId) async {
    Map<String, dynamic> map = {};
    map['userId'] = AppConst.user.userId;
    map['groupId'] = groupId;
    FormData data = FormData(map);
    Response response = await post('/account/delGroup', data);
    print(response.body);
    var body = BaseResponse.fromJson(response.body, (json) => {});
    if (body.success) {
      return body;
    } else {
      Fluttertoast.showToast(
        msg: body.message,
      );
      return null;
    }
  }

  @override
  Future<List<GroupEntity>?> getGroups(bool needAccounts) async {
    Map<String, dynamic> map = {};
    map['userId'] = AppConst.user.userId;
    FormData data = FormData(map);
    Response response = await post(
        needAccounts ? '/account/getGroupAccounts' : '/account/getGroups',
        data);
    print(response.body);
    var body = BaseResponse<List<GroupEntity>>.fromJson(
        response.body,
        (json) => ((json as List<dynamic>)
            .map((e) => GroupEntity.fromJson((e as Map<String, dynamic>)))
            .toList()));

    if (body.success) {
      return body.data ?? [];
    } else {
      Fluttertoast.showToast(
        msg: body.message,
      );
      return null;
    }
  }
}
