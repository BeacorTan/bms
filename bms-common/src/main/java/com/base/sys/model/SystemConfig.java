package com.base.sys.model;
import com.common.framework.base.BaseModel;

import javax.persistence.Column;
import javax.persistence.Table;
/**
 * 系统配置
 *
 *
 */
@Table(name="T_SYS_CONFIG")
public class SystemConfig extends BaseModel
{
	private static final long serialVersionUID = 1L;

	/**
	 * 配置CODE
	 */
	@Column(length = 64, nullable = false, name = "CONFIG_CODE")
	private String configCode;
	
	/**
	 * 标识名称
	 */
	@Column(length = 200, nullable = false, name = "CONFIG_NAME")
	private String configName;
	
	/**
	 * 备注
	 */
	@Column(length = 300, name = "CONFIG_DESC")
	private String configDesc;
	
	/**
	 * 此CONFIG_CODE下对应的value值
	 */
	@Column(length = 200, nullable = false, name = "CONFIG_VALUE")
	private String configValue;
	
	
	public SystemConfig()
	{
		
	}
	
	public SystemConfig(String id)
	{
		this.id = id;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigName() {
		return configName;
	}

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigDesc() {
		return configDesc;
	}

	public void setConfigDesc(String configDesc) {
		this.configDesc = configDesc;
	}

	public String getConfigValue() {
		return configValue;
	}

	public void setConfigValue(String configValue) {
		this.configValue = configValue;
	}
	
	
	
}
