package kz.eugales.re4.dependencies.components;

import dagger.Component;
import kz.eugales.re4.CompanyFragment;
import kz.eugales.re4.anotations.PerApplication;
import kz.eugales.re4.dependencies.modules.ApiModule;

/**
 * Created by Adil on 11.08.2016.
 */

@PerApplication
@Component(modules = ApiModule.class, dependencies = {NetworkComponent.class})
public interface ApiComponent {

    void inject(CompanyFragment companyFragment);

}
