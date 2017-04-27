import utils.MaxWeightException;
import utils.NodeNotFoundException;
import utils.SwapFailedException;

import java.util.ArrayList;

/**
 * Created by andream16 on 25.04.17.
 */
public class Exchange {

    RouteList routes;
    Helper helper;

    public Exchange(RouteList routes, Helper helper){
        this.routes = routes;
        this.helper = helper;
    }

    public RouteList findBestExchange() throws MaxWeightException, NodeNotFoundException {

        //For each route
        for(Route route : routes){

            ArrayList<Node> currentNodes = route.nodeList;
            int currentRouteSize = currentNodes.size();
            int currentRouteIndex = routes.indexOf(route);

            //For each node in current route
            for(Node node : currentNodes){

                /** Swaps on the same Route **/
                //If currentNode is a Warehouse node indeed we skip it
                if(node.getType().equals(Values.nodeType.WAREHOUSE)) continue;

                //Get current node index
                int currNodeIndex = currentNodes.indexOf(node);

                //Starting from the next node
                for( int i=currNodeIndex+1; i<currentRouteSize; i++ ){
                    //Get next's node index
                    Node currentNode = currentNodes.get(i);

                    //If the next node is a warehouse skip
                    if(currentNode.getType().equals(Values.nodeType.WAREHOUSE)) continue;

                    try {
                        helper.swapNodes(node, currentNode);
                    } catch (SwapFailedException e) {}
                }

                /** Swaps on Other Routes **/
                for(int j=currentRouteIndex+1; j<routes.size(); j++){

                    Route otherRoute = routes.get(j);

                    ArrayList<Node> otherRouteNodes = otherRoute.nodeList;

                    //For each node in otherRoute
                    for(Node otherRouteNode : otherRouteNodes){

                        if(otherRouteNode.getType().equals(Values.nodeType.WAREHOUSE)) continue;
                        try {
                            helper.swapNodes(node, otherRouteNode);
                        } catch (SwapFailedException e) {}
                    }

                }

            }

        }

        return routes;

    }

}