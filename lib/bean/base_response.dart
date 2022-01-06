import 'package:json_annotation/json_annotation.dart';

part 'base_response.g.dart';

@JsonSerializable(genericArgumentFactories: true)
class BaseResponse<T> {
  int code = 0;
  bool success = true;
  String message;
  T? data;

  BaseResponse(
      {required this.code, required this.success,required this.message, this.data});

  factory BaseResponse.fromJson(
          Map<String, dynamic> json, T Function(dynamic json) fromJsonT) =>
      _$BaseResponseFromJson(json, fromJsonT);

  Map<String, dynamic> toJson(Object? Function(T value) toJsonT) =>
      _$BaseResponseToJson(this, toJsonT);
}
