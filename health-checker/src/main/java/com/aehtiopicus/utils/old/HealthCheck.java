//package com.nuskin.ebiz.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Class that holds information about a System and its dependencies health.
// * <p>
// * if not healthy, meaning that, at least, one of the systems / service being consumed by this service is not healthy as well.
// * <p>
// * Created by Miguel Senosiain on 16/10/14.
// */
//public class HealthCheck {
//
//    private Boolean healthy;
//    private List<SystemHealth> systems;
//
//    public HealthCheck(Boolean healthy, List<SystemHealth> systems) {
//        this.healthy = healthy;
//        this.systems = systems;
//    }
//
//    public HealthCheck(Boolean healthy) {
//        this.healthy = healthy;
//        this.systems = new ArrayList<SystemHealth>();
//    }
//
//    public Boolean isHealthy() {
//        return healthy;
//    }
//
//    public void setHealthy(Boolean healthy) {
//        this.healthy = healthy;
//    }
//
//    public List<SystemHealth> getSystems() {
//        return systems;
//    }
//
//    public void setSystems(List<SystemHealth> systems) {
//        this.systems = systems;
//    }
//}
