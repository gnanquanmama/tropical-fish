package com.mcoding.util;

/**
 * 响应码：0表示成功，其他表示错误或失败
 *
 */
public enum ResponseCode {

	Success("200", "base_success", "操作成功"), 
	ERROR("500", "base_error", "系统内部异常"), 
	Fail("400", "base_fail", "操作失败"), 
	Param_Error("400", "base_param_error", "参数异常"), 
	Format_Error("415","base_format_error", "格式错误"), 
	METHOD_NO_SUPPORT("405","base_method_no_support", "不支持当前请求方法"), 
	No_Exist("410", "base_record_no_exist", "记录不存在或已被删除"),
	
	Chinese_Cannot_Be_Null("403","chinese_cannot_be_null","中文不能为空"),
	
	Account_Permission_denied("403", "base_permission_denied", "没有操作权限"),
	Account_No_Login("401", "base_account_no_login", "没有登录,或登录已过期"),
	
	DATABASE_LENGTH_ERROR("403","database_length_error","输入的参数长度超标"),
	DATABASE_PARSE_ERROR("403","database_parse_error","输入的参数类型或格式有误"),
	DATABASE_OPERATE_ERROR("403","database_operate_error","数据库操作异常"),
	
	
	Account_Create_Fail("403","base_account_cre_fail","创建账号失败"),
	Account_Expired("403", "base_account_expired", "帐号已过期"),
	Account_Disabled("403", "base_account_disabled", "帐号已禁用"),
	Account_Locked("403", "base_account_locked", "帐号已锁定"),
	The_Same_Account("403","base_the_same_account","已经存在相同的登录账号"),
	Accouont_Password_Expired("403", "base_password_expired", "密码过期"),
	Account_Password_Worng("403", "base_account_password_worng", "用户名或密码错误"),
	Account_Username_Not_Found("401", "base_account_username_not_found", "找不到该帐号"),
	Account_Sessioin_Expired("403", "base_account_session_expired", "session会话异常"),
	Account_Captcha_Not_Found("403","base_account_captcha_not_found","验证码异常"),
	Account_Captcha_Worng("403","base_account_captcha_worng","验证码有误"),
	User_Not_Found("403","user_not_found","找不到该用户，无法操作"),
	Can_Not_Be_Null("403","base_canot_be_null","不能为空"),
	Is_Exists("403","base_is_exists","已存在,不可重复"),
	Admin_Not_Allow_Oper("403","admin_not_allow_oper","管理员,不允许操作"),
	Id_Is_Blank("403","id_is_blank","id为空，操作失败"),
	Unable_System("403","unable_system","无法匹配系统"),
	Illegal_Opertion("400","base_illegal_opertion","不合法操作"),
	Donot_Exists("403","do_not_exists","不存在,无法操作"),
	Data_Error("403","base_data_error","数据异常"),
	Unable_To_Parse("403","unable_to_parse","参数格式,无法解析"),
	Query_Condition_Cannot_Be_Empty("403","query_condition_cannot_be_empty","查询条件不能为空"),
	Parameter_Incomplete("403","parameter_incomplete","参数不完整"),
	Must_Be_Unique("403","must_be_unique","必须唯一"),
	Unrecognized("400","unrecognized","无法识别"),
	IsNull("403","isNull","为空,无法操作"),
	Has_Be_Confirm("403","has_be_confirm","已经确认,无法再修改"),
	Already_In_Use("403","already_in_use","已被使用"),
	Achieve_Fail("403","achieve_fail","获取失败");
	
	private String httpCode;
	private String key;
	private String msg;

	private ResponseCode(String httpCode, String key, String msg) {
		this.httpCode = httpCode;
		this.key = key;
		this.msg = msg;
	}

	
	public String getCode() {
		return httpCode;
	}

	public String getKey() {
		return key;
	}

	public String getMsg() {
		return msg;
	}

}
