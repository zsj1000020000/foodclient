package cn.edu.hstc.food.client.domain;

import java.util.Date;

public class Store {

	private Integer id;

	private String name;

	private String info;

	private String pic;

	private String phone_number;

	private Date updateTime;

	private Boolean isDelete;

	public Store() {
	}

	public Store(Integer id, String name, String info, String pic,
			String phone_number, Date updateTime, Boolean isDelete) {
		super();
		this.id = id;
		this.name = name;
		this.info = info;
		this.pic = pic;
		this.phone_number = phone_number;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getPic() {
		return pic;
	}

	public void setPic(String pic) {
		this.pic = pic;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Boolean getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(Boolean isDelete) {
		this.isDelete = isDelete;
	}

}
