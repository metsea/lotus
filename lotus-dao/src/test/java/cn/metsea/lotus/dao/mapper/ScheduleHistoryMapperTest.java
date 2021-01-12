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
public class ScheduleHistoryMapperTest {

    @Autowired
    private ScheduleMapper scheduleHistoryMapper;

    @Test
    public void TestInsert() {
        Schedule scheduleHistory = new Schedule();
        scheduleHistory.setJobId(10);
        scheduleHistory.setFireTime(new Date());
        int insert = scheduleHistoryMapper.insert(scheduleHistory);
        Assert.assertEquals(insert, 1);
    }

    @Test
    public void testUpdate() {
        Schedule scheduleHistory = new Schedule();
        scheduleHistory.setId(2L);
        scheduleHistory.setFireTime(new Date());
        int updateById = scheduleHistoryMapper.updateById(scheduleHistory);
        Assert.assertEquals(updateById, 1);
    }

    @Test
    public void testDelete() {
        int deleteById = scheduleHistoryMapper.deleteById(4);
        Assert.assertEquals(deleteById, 1);
    }

    @Test
    public void testQuery() {
        Schedule scheduleHistory = scheduleHistoryMapper.selectById(3);
        Assert.assertNotNull(scheduleHistory);
    }

    @Test
    public void testQueryList() {
        Set set = new TreeSet<Integer>();
        set.add(2);
        set.add(3);
        List list = scheduleHistoryMapper.selectBatchIds(set);
        Assert.assertNotNull(list);
    }

    @Test
    public void testQueryPage() {
        Page<Schedule> page = new Page(1, 2);
        QueryWrapper wrapper = new QueryWrapper<Schedule>();
        wrapper.orderByDesc("id");
        Page page1 = scheduleHistoryMapper.selectPage(page, wrapper);
        List<Schedule> records = page1.getRecords();
        Assert.assertNotNull(records);
    }
}
