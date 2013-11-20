package org.cr.crawler.common.service;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import java.util.List;
import java.util.Map;

import org.cr.crawler.common.model.Task;
import org.cr.crawler.common.model.TaskConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.stereotype.Service;

/**
 * Author: caorong Date: 13-11-17 Time: 上午12:04 To change this template use File
 * | Settings | File Templates.
 */
@Service
public class TaskService {

	private Logger logger = LoggerFactory.getLogger(TaskService.class);
	@Autowired
	private MongoOperations mongoTemplate;

	public void updateState(Task task) {
		mongoTemplate.updateFirst(query(where("id").is(task.getId())),
				update("state", task.getState()), Task.class);
	}

	public void submitTaskFromListMap(List<Map<String, String>> detailUrls,
			Task oriTask) {
		List<TaskConfig> taskConfigs = mongoTemplate.find(
				query(where("id").is(oriTask.getTaskConfigId())),
				TaskConfig.class);
		TaskConfig taskConfig = null;
		if (taskConfigs.size() > 0) {
			taskConfig = taskConfigs.get(0);
		}
		for (Map<String, String> map : detailUrls) {
			if (map.get("url") == null) {
				continue;
			}
			// 这里检测方式下载重复视频
			if (this.checkDetailUrl(map.get("url"))) {
				continue;
			}
			Task task = new Task();
//			if (taskConfig != null) {
//				task.setPriority(taskConfig.getPriority());
//				task.setConfigId(taskConfig.getId());
//				task.setGroupname(taskConfig.getGroupname());
//			}
//			task.setState(Constant.TASK_STATE_INITIAL);
//			task.setResultState(Constant.TASK_STATE_INITIAL);
//			task.setUrl(map.get("url"));
//			task.setType(Constant.DETAIL_TASK_TYPE);
//			task.setAlbumId(album.getId());
//			task.setAudioId(audioId);
//			task.setAlbumTitle(audio.getAlbumTitle());
//			task.setAudioTitle(audio.getTitle());
//			task.setCreateTime(new Date(System.currentTimeMillis()));

			mongoTemplate.insert(task);
		}

	}

	private boolean checkDetailUrl(String url) {
		List<Task> result = mongoTemplate.find(query(where("url").is(url)),
				Task.class);
		if (result.size() > 0)
			return true;
		else
			return false;
	}

}
