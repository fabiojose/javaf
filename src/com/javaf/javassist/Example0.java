package com.javaf.javassist;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod;
import javassist.NotFoundException;

import org.apache.commons.digester.Digester;

public class Example0 {

	@SuppressWarnings("unchecked")
	public static void main(String[] args) {
		
		final ClassPool _pool = ClassPool.getDefault();
		
		try{
			final CtClass _cc        = _pool.get("fabiojm.javassist.Leg");
			final CtMethod _setColor = _cc.getDeclaredMethod("setColor");
			_setColor.insertBefore("{System.out.println(\"Teste!!!\");}");
			
			final Class _class       = _pool.toClass(_cc);
			final Object _instance   = _class.newInstance();
			final Method _setColor1 = _class.getMethod("setColor", new Class[]{Color.class});
			_setColor1.invoke(_instance, new Object[]{Color.GREEN});
			//_instance.setColor(Color.GREEN);
		
			
			//_cc.writeFile();
			_cc.detach();
			/*final Class _class     = _pool.toClass(_cc);
			final Object _instance = _class.newInstance();
	
			System.out.println(_instance);
			Leg _leg = (Leg)_instance;
			_leg.setColor(Color.BLACK);
			*/
			_cc.defrost();
			_setColor.setBody("{System.out.println($1);System.out.println(666);\n System.out.println(\"Teste!!!\");}");
			
			//_pool.toClass(_cc);
			//((Leg)_pool.toClass(_cc).newInstance()).setColor(Color.GREEN);
			//_leg.setColor(Color.BLACK);
			final Object _instance2 = _class.newInstance();
			_class.getMethod("setColor", new Class[]{Color.class}).invoke(_instance2, new Object[]{Color.BLACK});
			
			_pool.importPackage("org.apache.commons.digester*");
			final CtClass _newone     = _pool.makeClass("br.com.bradesco.web.galg.util.javassist.SetupDigester");
			
			final CtMethod _bootstrap = CtMethod.make("public void bootstrap(org.apache.commons.digester.Digester digester){System.out.println($1);\nSystem.out.println(0);}", _newone); 
			_newone.addMethod(_bootstrap);
			
			final Class _newonec  = _pool.toClass(_newone);
			final Object _newoneo = _newonec.newInstance();
			System.out.println(_newoneo);
			System.out.println(_newonec.getMethods());
			for(Method _method : _newonec.getMethods()){
				System.out.println(_method);
				
				if(_method.getName().equals("bootstrap")){
					_method.invoke(_newoneo, new Object[]{new Digester()});
				}
			}
			
		}catch(NotFoundException _e){
			_e.printStackTrace();
			
		}catch(CannotCompileException _e){
			_e.printStackTrace();

		} catch(IllegalAccessException _e){
			_e.printStackTrace();
		}catch(InstantiationException _e){
			_e.printStackTrace();
		}catch(InvocationTargetException _e){
			_e.printStackTrace();
		}catch(NoSuchMethodException _e){
			_e.printStackTrace();
		}
	}

}
