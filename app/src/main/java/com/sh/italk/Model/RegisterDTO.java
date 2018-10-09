package com.sh.italk.Model;

/**
 * author： TongGuHermit
 * created on： 2018/10/9 14:48
 */

public class RegisterDTO {

	/**
	 * code : 200
	 * info : {"token":"742aa0a21287fe69879903f5ccc2002f","accid":"15867457982","name":""}
	 */
	private String desc;
	private String code;
	private InfoBean info;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public InfoBean getInfo() {
		return info;
	}

	public void setInfo(InfoBean info) {
		this.info = info;
	}

	public static class InfoBean {
		/**
		 * token : 742aa0a21287fe69879903f5ccc2002f
		 * accid : 15867457982
		 * name :
		 */

		private String token;
		private String accid;
		private String name;

		public String getToken() {
			return token;
		}

		public void setToken(String token) {
			this.token = token;
		}

		public String getAccid() {
			return accid;
		}

		public void setAccid(String accid) {
			this.accid = accid;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
