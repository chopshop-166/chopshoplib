package com.chopshop166.chopshoplib.maps;

import java.util.List;
import edu.wpi.first.math.estimator.PoseEstimator;

public class VisionMap {

    /** Camera-based position estimators. */
    public final List<CameraSource> visionSources;

    public VisionMap(final List<CameraSource> visionSources) {
        this.visionSources = visionSources;
    }

    public <T> void updateData(final PoseEstimator<T> estimator) {
        for (var source : this.visionSources) {
            var results = source.camera.getAllUnreadResults();
            if (!results.isEmpty()) {
                var estimate = source.estimator.update(results.get(results.size() - 1));
                estimate.ifPresent(est -> {
                    estimator.addVisionMeasurement(est.estimatedPose.toPose2d(),
                            est.timestampSeconds);
                });
            }
        }
    }
}
