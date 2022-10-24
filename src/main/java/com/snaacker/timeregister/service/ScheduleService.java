package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.model.ScheduleRequest;
import com.snaacker.timeregister.model.ScheduleResponse;
import com.snaacker.timeregister.model.TimeRegisterGenericResponse;
import com.snaacker.timeregister.persistent.Restaurant;
import com.snaacker.timeregister.persistent.Schedule;
import com.snaacker.timeregister.repository.RestaurantRepository;
import com.snaacker.timeregister.repository.ScheduleRepository;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ScheduleService {

    private ScheduleRepository scheduleRepository;
    private RestaurantRepository restaurantRepository;

    @Autowired
    public ScheduleService(
            final ScheduleRepository scheduleRepository,
            final RestaurantRepository restaurantRepository) {
        this.scheduleRepository = scheduleRepository;
        this.restaurantRepository = restaurantRepository;
    }

    public TimeRegisterGenericResponse<ScheduleResponse> getScheduleOfRestaurant(int restaurantId) {
        //    TODO: Finish me
        return null;
    }

    public ScheduleResponse createSchedule(Long id, ScheduleRequest scheduleRequest) {
        Restaurant restaurant = restaurantRepository.getById(id);
        if (null == restaurant) {
            throw new TimeRegisterBadRequestException("Restaurant does not exist");
        }
        Schedule newSchedule = new Schedule();
        newSchedule.setScheduleDate(scheduleRequest.getScheduleDate());
        newSchedule.setFromTime(scheduleRequest.getFromTime());
        newSchedule.setToTime(scheduleRequest.getToTime());
        Schedule schedule = scheduleRepository.save(newSchedule);
        return new ScheduleResponse(schedule);
    }

    public ScheduleResponse editSchedule(
            Long id, Long schedule_id, ScheduleRequest scheduleRequest) {
        Restaurant restaurant = restaurantRepository.getById(id);
        if (null == restaurant) {
            throw new TimeRegisterBadRequestException("Restaurant does not exist");
        }
        Schedule schedule = scheduleRepository.getById(schedule_id);
        if (null == schedule) {
            throw new TimeRegisterBadRequestException("Schedule record does not exist");
        }
        if (null != scheduleRequest.getScheduleDate()) {
            schedule.setScheduleDate(scheduleRequest.getScheduleDate());
        }
        if (null != scheduleRequest.getToTime()) {
            schedule.setToTime(scheduleRequest.getToTime());
        }
        if (null != scheduleRequest.getFromTime()) {
            schedule.setFromTime(scheduleRequest.getFromTime());
        }
        schedule.setUpdatedDate(new Date());
        return new ScheduleResponse(scheduleRepository.save(schedule));
    }
}
