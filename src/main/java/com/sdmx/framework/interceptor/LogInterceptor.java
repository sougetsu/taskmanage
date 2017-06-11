package com.sdmx.framework.interceptor;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.alibaba.fastjson.JSON;
import com.sdmx.framework.entity.LogInfo;
import com.sdmx.framework.service.ILogService;
import com.sdmx.framework.util.ResourceUtil;
import com.sdmx.framework.util.annotation.BussAnnotation;
import com.sdmx.framework.vo.JsonResult;
import com.sdmx.framework.vo.SessionInfo;

@Aspect  
public class LogInterceptor {
	/**
	 * Logger for this class
	 */
	private static final Logger logger = Logger.getLogger(LogInterceptor.class);
	
	@Autowired
	private ILogService logService;
    //标注该方法体为后置通知，当目标方法执行成功后执行该方法体  
	@AfterReturning("within(com.petecc.framework.service.IService+) && @annotation(rl)")  
    public void addLogSuccess(JoinPoint jp, BussAnnotation rl){
		Object[] parames = jp.getArgs();//获取目标方法体参数  
        String params = parseParames(parames); //解析目标方法体的参数  
        String className = jp.getTarget().getClass().toString();//获取目标类名  
        className = className.substring(className.indexOf("com"));  
        String signature = jp.getSignature().toString();//获取目标方法签名  
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        LogInfo log = new LogInfo();
        log.setLogId(java.util.UUID.randomUUID().toString());
        if(params.length()>200){
        	log.setArgument(params.substring(0, 200));
        }else{
        	log.setArgument(params);
        }
        log.setClassName(className);
        log.setMethodName(methodName);
        log.setFlag("0");
        log.setMemberId(sessionInfo.getUserId());
        log.setMemberName(sessionInfo.getLoginName());
        log.setIpAdress(sessionInfo.getIp());
        log.setOperationTime(new Date());
        
        try{
        logService.create(log);
        }catch(Exception e){
        	e.printStackTrace();
        	System.out.println("error");
        }
    }
	//当目标方法有异常抛出后执行该方法体  
    @AfterThrowing(pointcut="within(com.petecc.framework.service.IService+)", throwing="ex")
    public void addLog(JoinPoint jp, Exception ex){
        String className = jp.getTarget().getClass().toString();//获取目标类名  
        className = className.substring(className.indexOf("com"));  
        String signature = jp.getSignature().toString();//获取目标方法签名  
        String methodName = signature.substring(signature.lastIndexOf(".")+1, signature.indexOf("("));
        HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
        SessionInfo sessionInfo = (SessionInfo) request.getSession().getAttribute(ResourceUtil.getSessionInfoName());
        logger.error(sessionInfo.getLoginName()+"在执行"+className+"的"+methodName+"方法时出现了错误："+ex.getMessage());
    }
    
    /** 
     * Around 
     *  
     * @param joinPoint 
     * @return 
     * @throws Throwable 
     */  
    @Around(value="within(com.petecc.*.controller..*)")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {  
        Object obj=null;
        try{
        	obj = joinPoint.proceed();
        }catch(Exception e){
        	e.printStackTrace();
        	// 在这里判断异常，根据不同的异常返回错误。   
        	return JsonResult.failed("错误，请联系管理员", null);
        }
        return obj;
    } 
    /** 
     * 解析方法参数 
     * @param parames 方法参数 
     * @return 解析后的方法参数 
     */  
    private String parseParames(Object[] parames) {  
        StringBuffer sb = new StringBuffer();  
        for(int i=0; i<parames.length; i++){  
            if(i==parames.length-1){  
                sb.append(JSON.toJSONString(parames[i]));  
            }else{  
                sb.append(JSON.toJSONString(parames[i]) + ",");  
            }  
        }  
        String params = sb.toString();  
        params = params.replaceAll("(\"\\w+\":\"\",)", "");  
        params = params.replaceAll("(,\"\\w+\":\"\")", "");  
        return params;  
    }  
}
