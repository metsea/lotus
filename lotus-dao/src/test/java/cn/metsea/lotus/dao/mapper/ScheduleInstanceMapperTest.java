package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.ScheduleInstance;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * Schedule Instance Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class ScheduleInstanceMapperTest {

    @Autowired
    private ScheduleInstanceMapper scheduleInstanceMapper;

    @Test
    public void TestInsert() {
        ScheduleInstance scheduleInstance = new ScheduleInstance();
        scheduleInstance.setJobId(10);
        scheduleInstance.setFireTime(new Date());
        int insert = scheduleInstanceMapper.insert(scheduleInstance);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        ScheduleInstance scheduleInstance = new ScheduleInstance();
        scheduleInstance.setId(2L);
        scheduleInstance.setFireTime(new Date());
        int updateById = scheduleInstanceMapper.updateById(scheduleInstance);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = scheduleInstanceMapper.deleteById(2);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        ScheduleInstance scheduleInstance = scheduleInstanceMapper.selectById(3);
        Assert.assertNotNull(scheduleInstance);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = scheduleInstanceMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<ScheduleInstance> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<ScheduleInstance>();
        wrapper.orderByDesc("id");
        Page page1 = scheduleInstanceMapper.selectPage(page, wrapper);
        List<ScheduleInstance> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
