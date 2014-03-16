metauiDirectives.directive('muTree', ['$compile', function($compile) {
    return {
        restrict: 'A',
        /*scope: {},*/
        link: function ( scope, element, attrs ) {
            var options = {
                treeId: attrs['muTree'],
                nodeId: attrs.nodeId,
                nodeLabel: attrs.nodeLabel,
                nodeChildren: attrs.nodeChildren,
                nodeLabelTemplate: attrs['nodeLabelTemplate'],
                onNodeClick: attrs['onNodeClick'],
                data: []
            };

            var treeOptions = attrs['treeOptions'];
            if(treeOptions) {
                options = $.extend(options, scope[treeOptions]);
            }

            var tree =  new M.Tree(options);
            var config = tree.config;

            //tree id
            var treeId = config.treeId;
            //tree model
            var treeModel = attrs['treeModel'] || 'treeModel';
            //node id
            var nodeId = config.nodeId;
            //node label
            var nodeLabel = config.nodeLabel;
            //children
            var nodeChildren = config.nodeChildren;
            // is root
            var isRoot = attrs['isRoot'] || true;

            scope[treeModel] = config.data;
            scope.hasLabelTemplate = config.nodeLabelTemplate != undefined;

            //tree template
            var template =
                '<ul class="nav nav-list nav-pills nav-stacked mu-tree">' +
                    '<li data-ng-repeat="node in ' + treeModel + '">' +
                        '<a href="#">' +
                            '<img ng-src="{{node.icon}}" data-ng-click="' + treeId + '.selectNodeHead(node)"/>' +
                            '<i class="collapsed" data-ng-show="node.' + nodeChildren + '.length && node.collapsed && !node.icon" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
                            '<i class="expanded" data-ng-show="node.' + nodeChildren + '.length && !node.collapsed && !node.icon" data-ng-click="' + treeId + '.selectNodeHead(node)"></i>' +
                            '<i class="normal" data-ng-hide="node.' + nodeChildren + '.length || node.icon"></i> ' +
                            '<span data-ng-class="node.selected" ng-dblclick="' + treeId + '.selectNodeHead(node)" data-ng-click="' + treeId + '.selectNodeLabel(node)">' +
                                '<span data-ng-show="!hasLabelTemplate">{{node.' + nodeLabel + '}}</span>' +
                                '<span data-ng-show="hasLabelTemplate" data-ng-bind-html="' + config.nodeLabelTemplate + '(node)"></span>' +
                            '</span>' +
                        '</a>' +
                        '<div data-tree-options="' + treeOptions + '" data-is-root="false" data-mu-tree="' + treeId + '" data-ng-show="node.expanded" data-tree-id="' + treeId + '" data-tree-model="node.' + nodeChildren + '" data-node-id=' + nodeId + ' data-node-label=' + nodeLabel + ' data-node-children=' + nodeChildren + '' +
                            ' data-node-label-template="' + config.nodeLabelTemplate + '"></div>' +
                    '</li>' +
                '</ul>';

            //check tree id, tree model
            if( treeId && treeModel ) {
                //root node
                if(isRoot) {
                    //create tree object if not exists
                    scope[treeId] = scope[treeId] || {};

                    //if node head clicks,
                    scope[treeId].selectNodeHead = scope[treeId].selectNodeHead || function( selectedNode ){
                        //Collapse or Expand
//                        selectedNode.collapsed = !selectedNode.collapsed;
                        selectedNode.expanded = !selectedNode.expanded;
                    };

                    //if node label clicks,
                    scope[treeId].selectNodeLabel = scope[treeId].selectNodeLabel || function( selectedNode ){

                        //remove highlight from previous node
                        if( scope[treeId].currentNode && scope[treeId].currentNode.selected ) {
                            scope[treeId].currentNode.selected = undefined;
                        }

                        //set highlight to selected node
                        selectedNode.selected = 'selected';
                        if(config.onNodeClick) {
                            scope[config.onNodeClick](selectedNode);
                        }

                        //set currentNode
                        scope[treeId].currentNode = selectedNode;
                    };
                }

                //Rendering template.
                element.html('').append( $compile( template )( scope ) );
            }
        }
    };
}]);