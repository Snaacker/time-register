package com.snaacker.timeregister.service;

import com.snaacker.timeregister.exception.TimeRegisterBadRequestException;
import com.snaacker.timeregister.model.request.ScheduleRequest;
import com.snaacker.timeregister.model.response.ScheduleResponse;
import com.snaacker.timeregister.model.response.TimeRegisterGenericResponse;
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
        newSchedule.setScheduleType(scheduleRequest.getScheduleType());
        newSchedule.setClosingHour(scheduleRequest.getClosingHour());
        newSchedule.setOpeningHour(scheduleRequest.getOpeningHour());
        newSchedule.setSpecialDate(scheduleRequest.getSpecialDate());
        newSchedule.setSpecialDateName(scheduleRequest.getSpecialDateName());
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
        if (null != scheduleRequest.getScheduleType()) {
            schedule.setScheduleType(scheduleRequest.getScheduleType());
        }
        if (0 != scheduleRequest.getOpeningHour()) {
            schedule.setOpeningHour(scheduleRequest.getOpeningHour());
        }
        if (0 != scheduleRequest.getClosingHour()) {
            schedule.setClosingHour(scheduleRequest.getClosingHour());
        }
        if (null != scheduleRequest.getSpecialDate()) {
            schedule.setSpecialDate(scheduleRequest.getSpecialDate());
        }
        if (null != scheduleRequest.getSpecialDateName()) {
            schedule.setSpecialDateName(scheduleRequest.getSpecialDateName());
        }
        schedule.setUpdatedDate(new Date());
        return new ScheduleResponse(scheduleRepository.save(schedule));
    }
}
