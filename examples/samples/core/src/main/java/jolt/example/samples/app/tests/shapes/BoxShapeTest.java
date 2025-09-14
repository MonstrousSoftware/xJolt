package jolt.example.samples.app.tests.shapes;

import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Vector3;
import jolt.JoltNew;
import jolt.JoltTemp;
import jolt.enums.EActivation;
import jolt.enums.EMotionType;
import jolt.example.samples.app.jolt.Layers;
import jolt.example.samples.app.tests.Test;
import jolt.math.Quat;
import jolt.math.Vec3;
import jolt.physics.body.Body;
import jolt.physics.body.BodyCreationSettings;
import jolt.physics.collision.shape.BoxShape;

public class BoxShapeTest extends Test {

    private static int nextId = 1;

    @Override
    public void initialize() {
        // Floor
        createFloor();

        Body body1 = createBody(new Vector3(20, 1, 1), new Vector3(0, 10, 0), Quat.sIdentity());
        mBodyInterface.AddBody(body1.GetID(), EActivation.Activate);

        Body body2 = createBody(new Vector3(2, 3, 4), new Vector3(0, 10, 10), Quat.sRotation(Vec3.sAxisZ(), 0.25f * MathUtils.PI));
        mBodyInterface.AddBody(body2.GetID(), EActivation.Activate);

        // Methods that return a Value c++ object will be replaced every time its called. Save its value before calling again.
        Quat quatX3 = Quat.sRotation(Vec3.sAxisX(), 0.25f * MathUtils.PI);
        float q1X = quatX3.GetX();
        float q1Y = quatX3.GetY();
        float q1Z = quatX3.GetZ();
        float q1W = quatX3.GetW();
        Quat quatZ3 = Quat.sRotation(Vec3.sAxisZ(), 0.25f * MathUtils.PI);
        float q2X = quatZ3.GetX();
        float q2Y = quatZ3.GetY();
        float q2Z = quatZ3.GetZ();
        float q2W = quatZ3.GetW();
        Quaternion q1 = new Quaternion(q1X, q1Y, q1Z, q1W);
        Quaternion q2 = new Quaternion(q2X, q2Y, q2Z, q2W);
        Quaternion mul = q1.mul(q2);
        quatX3.SetX(mul.x);
        quatX3.SetY(mul.y);
        quatX3.SetZ(mul.z);
        quatX3.SetW(mul.w);
        Body body3 = createBody(new Vector3(0.5f, 0.75f, 1.0f), new Vector3(0, 10, 20), quatX3);
        mBodyInterface.AddBody(body3.GetID(), EActivation.Activate);
    }

    private Body createBody(Vector3 inHalfExtent, Vector3 inPosition, Quat inRotation) {
        float scale = getWorldScale();

        Vec3 inHalfExtentJolt = JoltTemp.Vec3_1(inHalfExtent.x, inHalfExtent.y, inHalfExtent.z);
        Vec3 inPositionJolt = JoltTemp.Vec3_2(inPosition.x, inPosition.y, inPosition.z);
        BoxShape bodyShape = new BoxShape(inHalfExtentJolt);
        BodyCreationSettings bodySettings = JoltNew.BodyCreationSettings(bodyShape, inPositionJolt, inRotation, EMotionType.Dynamic, Layers.MOVING);
        Body body = mBodyInterface.CreateBody(bodySettings);
        bodySettings.dispose();
        body.SetUserData(nextId++);
        return body;
    }
}