package mc.krakow.graph

import mc.krakow.data.browse.DataBrowser

interface DataToGraphImporter {
    /**
     * @param dataBrowser the browser that is connected to DB
     * @return a graph that is created based on the data provided by `dataBrowser`
     * */
    fun createGraph(dataBrowser: DataBrowser): TramConnectionsGraph
}
