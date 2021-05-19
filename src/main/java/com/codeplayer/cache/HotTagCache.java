package com.codeplayer.cache;

import com.codeplayer.dto.HotTagDTO;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

@Component
@Data
public class HotTagCache {
    private List<String> hots = new ArrayList<>();

    //topN问题
    public void updateTags(Map<String, Long> priorities) {
        int max = 12;//最大热门标签数
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>(max);//用于排序

        priorities.forEach((name, priority) -> {
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setName(name);
            hotTagDTO.setPriority(priority);

            if (priorityQueue.size() < max) {//热门标签个数小于max就直接往里面放当前标签
                priorityQueue.add(hotTagDTO);
            }else {//热门标签个数大于等于max
                HotTagDTO peek = priorityQueue.peek();//拿出热门标签中的最小标签
                if (hotTagDTO.compareTo(peek) > 0) {//当前标签比最小标签大
                    priorityQueue.poll();
                    priorityQueue.add(hotTagDTO);
                }
            }
        });

        List<String> sortedTags = new ArrayList<>();
        //一个一个拿出热门标签
        HotTagDTO poll = priorityQueue.poll();
        while (poll != null) {
            sortedTags.add(0, poll.getName());
            poll = priorityQueue.poll();
        }
        hots = sortedTags;
    }
}
