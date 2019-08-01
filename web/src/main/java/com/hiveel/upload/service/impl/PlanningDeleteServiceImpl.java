package com.hiveel.upload.service.impl;

import com.hiveel.core.exception.ParameterException;
import com.hiveel.core.exception.UnauthorizationException;
import com.hiveel.core.exception.util.ParameterExceptionUtil;
import com.hiveel.core.log.LogLevel;
import com.hiveel.core.log.util.LogUtil;
import com.hiveel.upload.service.PlanningDeleteService;
import com.hiveel.upload.model.PlanningDelete;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("planningDeleteService")
public class PlanningDeleteServiceImpl implements PlanningDeleteService {

    @Override
    public boolean delete(PlanningDelete planningDelete) throws ParameterException, UnauthorizationException {
        boolean result = new File(planningDelete.getFullPath()).delete();
        //删除大图
        boolean deleteBig = new File(planningDelete.getBigFullPath()).delete();
        if (!deleteBig) {
            // 失败可能原因，不存在
            LogUtil.info("尝试删除大图失败, 图片: " + planningDelete.getBigFullPath() + "。 对应小图删除 " + (result ? "成功" : "失败"));
        }
        return result;
    }
}
