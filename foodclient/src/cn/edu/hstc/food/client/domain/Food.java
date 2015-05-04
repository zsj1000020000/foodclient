package cn.edu.hstc.food.client.domain;

import java.util.Date;

public class Food {

    private Integer id;
	
	private String name;
	
	private String info;
	
	private String pic;
	
	private String price;
	
	private Date updateTime;

	private Boolean isDelete;
	
	private Integer sid;
	
	

	public Food(Integer id, String name, String info, String pic, String price,
			Date updateTime, Boolean isDelete, Integer sid) {
		super();
		this.id = id;
		this.name = name;
		this.info = info;
		this.pic = pic;
		this.price = price;
		this.updateTime = updateTime;
		this.isDelete = isDelete;
		this.sid = sid;
	}

	public Food() {
		super();
		// TODO Auto-generated constructor stub
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
	
	

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
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
	
	

	public Integer getSid() {
		return sid;
	}

	public void setSid(Integer sid) {
		this.sid = sid;
	}

	@Override
	public String toString() {
		return "Food [id=" + id + ", name=" + name + ", info=" + info
				+ ", pic=" + pic + "]";
	}
	
	
}
