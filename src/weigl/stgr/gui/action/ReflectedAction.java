package weigl.stgr.gui.action;

import java.awt.event.ActionEvent;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import weigl.stgr.gui.StgrAdvGui;

public class ReflectedAction extends StgrAdvGuiAction {
  private static final long serialVersionUID = 7752172043503319281L;

  private String m_methodName;

  public ReflectedAction(StgrAdvGui application, String methodName) {
    super(application);
    setMethodName(methodName);
  }

  @SuppressWarnings("unchecked")
  public void actionPerformed(ActionEvent e) {
    Class<StgrAdvGui> clazz = (Class<StgrAdvGui>) m_application.getClass();

    try {
      Method method = clazz.getMethod(getMethodName(), ActionEvent.class);
      method.invoke(m_application, e);
    } catch (NoSuchMethodException e1) {
      try {
        Method method = clazz.getMethod(getMethodName(), null);
        method.invoke(m_application, e);
      } catch (Exception e2) {
        e2.printStackTrace();
      }
    } catch (Exception e1) {
      e1.printStackTrace();
    }

  }

  public String getMethodName() {
    return m_methodName;
  }

  public void setMethodName(String methodName) {
    m_methodName = methodName;
  }
}
