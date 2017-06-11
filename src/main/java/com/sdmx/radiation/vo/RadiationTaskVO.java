package com.sdmx.radiation.vo;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.sdmx.framework.util.JsonDateSerializer;


public class RadiationTaskVO implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -692026257659425526L;

	public interface CreateTypeCheck {
	}
	
	private int page;
	private int rows;
	private String sort;
	private String order;
	
	/**
	 * 基本信息
	 */
	private long  radiationId; //任务单id
	private String taskNo;//任务单编号
	@NotBlank(message="电路型号不能为空",groups=CreateTypeCheck.class)
	private String dlmc;
	@NotEmpty(message="电路型号不能为空",groups=CreateTypeCheck.class)
	@Length(max=500,message="电路型号长度必须在500位以内",groups = CreateTypeCheck.class)
	private String dlxh;//电路型号
	@NotEmpty(message = "电路类别不能为空", groups = CreateTypeCheck.class)
	@Length(max=500,message="电路类别长度必须在500位以内",groups = CreateTypeCheck.class)
	private String dllb;//电路类别
	private String ktlb;//课题类别
	private String kth;//课题号
	@NotEmpty(message="电路型号不能为空")
	private String yfbm;//研发部门
	private String xmfzr;//项目负责人
	@NotNull(message="电路型号不能为空")
	private String lxdh;//联系电话
	private String yhdw;//用户单位
	private String jzdw;//监制单位
	private String wxxh;//卫星型号
	private String dlzfzr;//单粒子负责人
	private String zjlfzr;//总剂量负责人
	/**
	 * 电路信息
	 */
	private String gycc;//工艺尺寸
	private String sccj;//生产厂家
	private String zcgy;//制造工艺
	private String sjgy;//双极工艺
	private String xgbh;//详规编号
	private String fzxs;//封装形式
	private String gzdy;//工作电压
	private String dcxt;//电测系统
	private String ccws;//存储位数
	private String dxpl;//典型频率
	private String zldj;//质量等级
	private String cfqs;//触发器数
	private String dlms;//电路门数
	private String zgpl;//最高频率
	/**
	 * 辐射指标
	 */
	private String seufz;//SEU阈值
	private String seuzgcwl;//SEU在轨错误率
	private String ssfz;//闩锁阈值
	private String sefifz;//SEFI阈值
	private String sefizgcwl;//SEFI在轨错误率
	private String zzdlz;//质子单粒子
	private String zjl;//总剂量
	private String wyss;//位移损伤
	private String stjll;//瞬态剂量率
	

	/**
	 * 试验文档
	 */
//	private Set<TestAttachment> detailSpecNum = new  HashSet<TestAttachment>();//详细规范号
//	private Set<TestAttachment> singleParticlePlan = new  HashSet<TestAttachment>();//单粒子方案
//	private Set<TestAttachment> singleParticleReport = new  HashSet<TestAttachment>();//单粒子报告
//	private Set<TestAttachment> singleParticleData = new  HashSet<TestAttachment>();//单粒子数据
//	private Set<TestAttachment> otherDocument = new  HashSet<TestAttachment>();//其他文档
//	private Set<TestAttachment> integralDosePlan = new  HashSet<TestAttachment>();//总剂量方案
//	private Set<TestAttachment> integralDoseReport = new  HashSet<TestAttachment>();//总剂量报告
//	private Set<TestAttachment> integralDoseData = new  HashSet<TestAttachment>();//总剂量数据
	

	private String bz;//备注
	private Date createtime;//任务单创建时间
	private Date updatetime;//更新时间
	
	public long getRadiationId() {
		return radiationId;
	}

	public void setRadiationId(long radiationId) {
		this.radiationId = radiationId;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getDlmc() {
		return dlmc;
	}

	public void setDlmc(String dlmc) {
		this.dlmc = dlmc;
	}

	public String getDlxh() {
		return dlxh;
	}

	public void setDlxh(String dlxh) {
		this.dlxh = dlxh;
	}

	public String getDllb() {
		return dllb;
	}

	public void setDllb(String dllb) {
		this.dllb = dllb;
	}

	public String getKtlb() {
		return ktlb;
	}

	public void setKtlb(String ktlb) {
		this.ktlb = ktlb;
	}

	public String getKth() {
		return kth;
	}

	public void setKth(String kth) {
		this.kth = kth;
	}

	public String getYfbm() {
		return yfbm;
	}

	public void setYfbm(String yfbm) {
		this.yfbm = yfbm;
	}

	public String getXmfzr() {
		return xmfzr;
	}

	public void setXmfzr(String xmfzr) {
		this.xmfzr = xmfzr;
	}

	public String getLxdh() {
		return lxdh;
	}

	public void setLxdh(String lxdh) {
		this.lxdh = lxdh;
	}

	public String getYhdw() {
		return yhdw;
	}

	public void setYhdw(String yhdw) {
		this.yhdw = yhdw;
	}

	public String getJzdw() {
		return jzdw;
	}

	public void setJzdw(String jzdw) {
		this.jzdw = jzdw;
	}

	public String getWxxh() {
		return wxxh;
	}

	public void setWxxh(String wxxh) {
		this.wxxh = wxxh;
	}

	public String getDlzfzr() {
		return dlzfzr;
	}

	public void setDlzfzr(String dlzfzr) {
		this.dlzfzr = dlzfzr;
	}

	public String getZjlfzr() {
		return zjlfzr;
	}

	public void setZjlfzr(String zjlfzr) {
		this.zjlfzr = zjlfzr;
	}

	public String getGycc() {
		return gycc;
	}

	public void setGycc(String gycc) {
		this.gycc = gycc;
	}

	public String getSccj() {
		return sccj;
	}

	public void setSccj(String sccj) {
		this.sccj = sccj;
	}

	public String getZcgy() {
		return zcgy;
	}

	public void setZcgy(String zcgy) {
		this.zcgy = zcgy;
	}

	public String getSjgy() {
		return sjgy;
	}

	public void setSjgy(String sjgy) {
		this.sjgy = sjgy;
	}

	public String getXgbh() {
		return xgbh;
	}

	public void setXgbh(String xgbh) {
		this.xgbh = xgbh;
	}

	public String getFzxs() {
		return fzxs;
	}

	public void setFzxs(String fzxs) {
		this.fzxs = fzxs;
	}

	public String getGzdy() {
		return gzdy;
	}

	public void setGzdy(String gzdy) {
		this.gzdy = gzdy;
	}

	public String getDcxt() {
		return dcxt;
	}

	public void setDcxt(String dcxt) {
		this.dcxt = dcxt;
	}

	public String getCcws() {
		return ccws;
	}

	public void setCcws(String ccws) {
		this.ccws = ccws;
	}

	public String getDxpl() {
		return dxpl;
	}

	public void setDxpl(String dxpl) {
		this.dxpl = dxpl;
	}

	public String getZldj() {
		return zldj;
	}

	public void setZldj(String zldj) {
		this.zldj = zldj;
	}

	public String getCfqs() {
		return cfqs;
	}

	public void setCfqs(String cfqs) {
		this.cfqs = cfqs;
	}

	public String getDlms() {
		return dlms;
	}

	public void setDlms(String dlms) {
		this.dlms = dlms;
	}

	public String getZgpl() {
		return zgpl;
	}

	public void setZgpl(String zgpl) {
		this.zgpl = zgpl;
	}

	public String getSeufz() {
		return seufz;
	}

	public void setSeufz(String seufz) {
		this.seufz = seufz;
	}

	public String getSeuzgcwl() {
		return seuzgcwl;
	}

	public void setSeuzgcwl(String seuzgcwl) {
		this.seuzgcwl = seuzgcwl;
	}

	public String getSsfz() {
		return ssfz;
	}

	public void setSsfz(String ssfz) {
		this.ssfz = ssfz;
	}

	public String getSefifz() {
		return sefifz;
	}

	public void setSefifz(String sefifz) {
		this.sefifz = sefifz;
	}

	public String getSefizgcwl() {
		return sefizgcwl;
	}

	public void setSefizgcwl(String sefizgcwl) {
		this.sefizgcwl = sefizgcwl;
	}

	public String getZzdlz() {
		return zzdlz;
	}

	public void setZzdlz(String zzdlz) {
		this.zzdlz = zzdlz;
	}

	public String getZjl() {
		return zjl;
	}

	public void setZjl(String zjl) {
		this.zjl = zjl;
	}

	public String getWyss() {
		return wyss;
	}

	public void setWyss(String wyss) {
		this.wyss = wyss;
	}

	public String getStjll() {
		return stjll;
	}

	public void setStjll(String stjll) {
		this.stjll = stjll;
	}
	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	@JsonSerialize(using = JsonDateSerializer.class)
	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
