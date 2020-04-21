package mycontroller;

import mycontroller.Dijkstra;
import mycontroller.ISearchAlgorithm;
import mycontroller.Node;
import swen30006.driving.Simulation;

import java.util.Comparator;

/**
 * Team number: W12-3
 * Team member: XuLin Yang(904904), Zhuoqun Huang(908525), Renjie Meng(877396)
 *
 * @create 2019-05-22 15:49
 *
 * description : This class is responsible for creating IStrategy.
 **/

public class StrategyFactory {
    /** The instance for factory. */
    private static StrategyFactory strategyFactory = null;


    /**
     * The constructor for StrategyFactory.
     */
    private StrategyFactory() {

    }

    /**
     * @return The StrategyFactory instance
     */
    public static StrategyFactory getInstance() {
        if (strategyFactory == null) {
            strategyFactory = new StrategyFactory();
        }

        return strategyFactory;
    }

    /**
     * @param strategyType : drive strategy type
     * @param comparator : conserve comparator
     * @param searchAlgorithm : graph search algorithm
     * @return : created drive strategy
     */
    private IStrategy createStrategy(IStrategy.StrategyType strategyType,
                                     Comparator<Node> comparator,
                                     ISearchAlgorithm searchAlgorithm) {
        switch (strategyType) {
 
            case EXPLORE:
                IStrategy exploreStrategy = new ExploreStrategy(comparator);
                exploreStrategy.registerISearchAlgorithm(searchAlgorithm);
                return exploreStrategy;
            case EXIT:
                IStrategy exitStrategy = new ExitStrategy(comparator);
                exitStrategy.registerISearchAlgorithm(searchAlgorithm);
                return exitStrategy;
            case PICKUP:
                IStrategy pickupStrategy = new ParcelPickupStrategy(comparator);
                pickupStrategy.registerISearchAlgorithm(searchAlgorithm);
                return pickupStrategy;
        }

        /* for completeness */
        return null;
    }

    /**
     * This method is responsible for creating IStrategy depends on given conserve mode.
     *
     * @param mode : The specified conserve mode.
     * @return : The IStrategy object specified by the given mode.
     */
    public IStrategy createConserveStrategy() {
        ISearchAlgorithm searchAlgorithm = new Dijkstra();
        /* If it is fuel conserve mode, initialize a fuel conserve strategy */
        FuelConserveStrategy fuelConserveStrategy = new FuelConserveStrategy();
        /* register strategy into the fuel conserve strategy */
        Comparator<Node> fuelComparator = new FuelConserveStrategy.FuelComparator();

        fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.PICKUP,  createStrategy(IStrategy.StrategyType.PICKUP,  fuelComparator,  searchAlgorithm));
        fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXIT,    createStrategy(IStrategy.StrategyType.EXIT,    fuelComparator,  searchAlgorithm));
        fuelConserveStrategy.registerIStrategy(IStrategy.StrategyType.EXPLORE, createStrategy(IStrategy.StrategyType.EXPLORE, fuelComparator, searchAlgorithm));

        return fuelConserveStrategy;
      

    }
}
