package cn.metsea.lotus.dao.mapper;

import cn.metsea.lotus.dao.entity.Schedule;
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
 * Schedule Mapper Test
 */
@RunWith(SpringRunner.class)
@SpringBootTest()
@Transactional
@Rollback()
public class ScheduleMapperTest {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Test
    public void TestInsert() {
        Schedule schedule = new Schedule();
        schedule.setJobId(10);
        schedule.setFireTime(new Date());
        int insert = scheduleMapper.insert(schedule);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        Schedule schedule = new Schedule();
        schedule.setId(2L);
        schedule.setFireTime(new Date());
        int updateById = scheduleMapper.updateById(schedule);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = scheduleMapper.deleteById(4);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        Schedule schedule = scheduleMapper.selectById(3);
        Assert.assertNotNull(schedule);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = scheduleMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<Schedule> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<Schedule>();
        wrapper.orderByDesc("id");
        Page page1 = scheduleMapper.selectPage(page, wrapper);
        List<Schedule> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
