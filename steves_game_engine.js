(function() {
    var layer;

    Plugin.register('steves_game_engine',
	{
        title: '!Steves Game Engine Plugin',
        author: 'steve6472',
        description: 'Stuff for my game engine',
        icon: 'crop_square',
        version: '0.0.1',
        variant: 'both',
        onload()
		{
			new Property(Face, 'string', 'layer', {exposed: true, default: () => "normal"});
			
			layer = new BarSelect('set_layer', {
				condition: () => !Project.box_uv && Cube.selected.length,
				category: 'uv',
				min_width: 68,
				value: 'normal',
				options: {
					'normal': 'normal',
					'overlay': 'overlay',
					'double_sided': 'double_sided'
				},
				onChange: function(slider, event) 
				{
					Undo.initEdit({elements: Cube.selected});
					console.log(slider.value);
					Cube.selected[0].faces[main_uv.face].layer = slider.value;
					Undo.finishEdit('Change Layer');
				}
			});
			
			Blockbench.on('update_selection', data => 
			{
				if (Format.id === 'free' && Cube.selected.length > 0)
				{
					layer.value = Cube.selected[0].faces[main_uv.face].layer;
					layer.set(Cube.selected[0].faces[main_uv.face].layer);
				}
			});
			
			Toolbox.add(layer);
        },
		
        onunload()
		{
            layer.delete();
        }
    });

})();