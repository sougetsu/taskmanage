package com.sdmx.radiation.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.sdmx.framework.entity.Member;

@Entity
@Table(name = "RT_RADIATIONTASK", schema="SDMX")
public class RadiationTask {
	
	/**
	 * 基本信息
	 */
	private long  radiationId; //任务单id
	private String taskNo;//任务单编号
	private String dlmc;//电路名称
	private String dlxh;//电路型号
	private String dllb;//电路类别
	private String ktlb;//课题类别
	private String kth;//课题号
	private String yfbm;//研发部门
	private String xmfzr;//项目负责人
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
	 * 试验进度
	 */
	private TestSchedule singleParticleSchedule = new  TestSchedule();//单粒子试验进度
	private TestSchedule integralDoseSchedule = new  TestSchedule();//总剂量试验进度
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
	
	/**
	 * 试验日志
	 */
	private Set<SingleParticleLog> singleParticleLog = new  HashSet<SingleParticleLog>();//单粒子试验日志
	private Set<IntegralDoseLog> integralDoseLog = new  HashSet<IntegralDoseLog>();//总剂量试验日志

	private String bz;//备注
	@Column(columnDefinition="Date default sysdate",nullable=false)
	private Date createtime;//任务单创建时间
	private Date updatetime;//更新时间
	private Member member;//创建人
	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "S_RDTASK_ID")
	@SequenceGenerator(name = "S_RDTASK_ID", sequenceName = "S_RDTASK_ID",allocationSize=1)
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
	
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="singleScheduleId")
	public TestSchedule getSingleParticleSchedule() {
		return singleParticleSchedule;
	}

	public void setSingleParticleSchedule(TestSchedule singleParticleSchedule) {
		this.singleParticleSchedule = singleParticleSchedule;
	}
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="integralScheduleId")
	public TestSchedule getIntegralDoseSchedule() {
		return integralDoseSchedule;
	}

	public void setIntegralDoseSchedule(TestSchedule integralDoseSchedule) {
		this.integralDoseSchedule = integralDoseSchedule;
	}
	@OneToMany(mappedBy="radiationTask",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<SingleParticleLog> getSingleParticleLog() {
		return singleParticleLog;
	}

	public void setSingleParticleLog(Set<SingleParticleLog> singleParticleLog) {
		this.singleParticleLog = singleParticleLog;
	}
	@OneToMany(mappedBy="radiationTask",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
	public Set<IntegralDoseLog> getIntegralDoseLog() {
		return integralDoseLog;
	}

	public void setIntegralDoseLog(Set<IntegralDoseLog> integralDoseLog) {
		this.integralDoseLog = integralDoseLog;
	}

	public String getBz() {
		return bz;
	}

	public void setBz(String bz) {
		this.bz = bz;
	}
	
	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	
	@OneToOne(cascade = CascadeType.DETACH,fetch = FetchType.EAGER)
    @JoinColumn(name="memberId")
	public Member getMember() {
		return member;
	}

	public void setMember(Member member) {
		this.member = member;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
}
