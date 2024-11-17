package com.javadbmanager.business.delegate;

import java.sql.SQLException;

import com.javadbmanager.business.delegate.menu.DefaultMenuProvider;
import com.javadbmanager.business.delegate.menu.MenuManager;
import com.javadbmanager.business.delegate.menu.MenuManagerImpl;
import com.javadbmanager.business.delegate.menu.MenuProvider;
import com.javadbmanager.business.delegate.menu.MenuType;
import com.javadbmanager.business.logic.ConnectionBeanBuilder;
import com.javadbmanager.business.logic.DataLayerProvider;
import com.javadbmanager.business.logic.DataService;
import com.javadbmanager.business.logic.DataServiceImpl;
import com.javadbmanager.business.logic.DefaultDataLayerProvider;
import com.javadbmanager.business.logic.EnvManagerService;
import com.javadbmanager.business.logic.EnvManagerServiceImpl;
import com.javadbmanager.business.logic.TableManagerService;
import com.javadbmanager.business.logic.TableManagerServiceImpl;
import com.javadbmanager.data.ConnectionBean;
import com.javadbmanager.presentation.Display;
import com.javadbmanager.presentation.DisplayCLI;
import com.javadbmanager.presentation.exceptions.EmptyValueException;

/**
 * The {@code BusinessDelegate} class acts as a central point for initializing
 * and managing
 * the application's core components. It decouples the presentation layer from
 * the business
 * logic by handling the instantiation and dependency injection of key services
 * and managers.
 * This approach simplifies the presentation layer and promotes better
 * organization and maintainability.
 */
public class BusinessDelegate {
    /**
     * Initializes the application by creating and configuring the necessary objects
     * and injecting dependencies. This method sets up the display, menu manager,
     * environment manager, data layer provider, table manager, data service, and
     * menu provider.
     * It also displays an initial message and loads the main menu.
     */
    public void init() {
        Display display = new DisplayCLI();
        MenuManager menuManager = new MenuManagerImpl(display);
        EnvManagerService envManagerService = EnvManagerServiceImpl.getInstance();

        DataLayerProvider dataLayerProvider = new DefaultDataLayerProvider(envManagerService);
        envManagerService.set("dataLayerProvider", dataLayerProvider);

        TableManagerService tableManagerService = new TableManagerServiceImpl(dataLayerProvider);
        envManagerService.set("tableManagerService", tableManagerService);

        DataService dataService = new DataServiceImpl(dataLayerProvider);
        envManagerService.set("dataService", dataService);

        MenuProvider menuProvider = new DefaultMenuProvider(
                display,
                menuManager,
                new ConnectionBeanBuilder(),
                envManagerService,
                tableManagerService,
                dataService);

        display.sendLog("Java DB Manager V 0.0.1");
        display.sendLog("Press enter for continue");
        display.scanLine();
        menuProvider.getMenuMap().forEach(menuManager::addMenu);
        //
        menuManager.load(MenuType.MainMenu);
    }
}
