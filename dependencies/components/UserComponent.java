package kz.eugales.re4.dependencies.components;

import dagger.Component;
import kz.eugales.re4.registration.ConfirmRegistrationFragment;
import kz.eugales.re4.MainActivity;
import kz.eugales.re4.registration.RegistrationFragment;
import kz.eugales.re4.anotations.PerApplication;
import kz.eugales.re4.dependencies.modules.UserModule;

/**
 * Created by Adil on 22.09.2016.
 */

@Component(modules = UserModule.class, dependencies = {NetworkComponent.class})
@PerApplication
public interface UserComponent {
    void inject(RegistrationFragment registrationFragment);
    void inject(ConfirmRegistrationFragment confirmRegistrationFragment);
    void inject(MainActivity mainActivity);

}
