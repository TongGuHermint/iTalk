package com.sh.italk.Model;

/**
 * author： TongGuHermit
 * created on： 2018/10/9 15:04
 */

public class UpdateTokenDTO {


	/**
	 * code : 200
	 * info : {"token":"3931e6997cdf1d9ab64a36cf5ce203ea","accid":"15067457982"}
	 */

	private String code;
	private InfoBean info;

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
		 * token : 3931e6997cdf1d9ab64a36cf5ce203ea
		 * accid : 15067457982
		 */

		private String token;
		private String accid;

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
	}
}
