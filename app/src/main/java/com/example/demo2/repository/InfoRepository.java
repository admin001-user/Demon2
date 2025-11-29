package com.example.demo2.repository;

import com.example.demo2.model.InfoItem;
import java.util.ArrayList;
import java.util.List;

public class InfoRepository {
    public List<InfoItem> loadItems() {
        List<InfoItem> items = new ArrayList<>();
        items.add(new InfoItem("最新科技动态", "科技", "人工智能技术在各个领域的应用越来越广泛，正在改变我们的生活方式。", "2024-01-15"));
        items.add(new InfoItem("健康生活小贴士", "健康", "保持良好的作息规律，适量运动，健康饮食是维持身体健康的关键。", "2024-01-14"));
        items.add(new InfoItem("财经市场分析", "财经", "全球经济形势持续变化，投资者需要保持理性，做好风险管理。", "2024-01-13"));
        items.add(new InfoItem("教育创新模式", "教育", "在线教育平台为学习者提供了更加灵活和个性化的学习体验。", "2024-01-12"));
        items.add(new InfoItem("环保生活方式", "环保", "从日常生活做起，减少碳排放，保护我们共同的地球家园。", "2024-01-11"));
        return items;
    }
}

