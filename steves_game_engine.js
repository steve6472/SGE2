(function() {
    var layer;
	
	var collision, hitbox, disableOtherCull;

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
			new Property(Face, 'boolean', 'disable_other_cull', {exposed: true, default: () => false});
			new Property(Cube, 'boolean', 'collision', {exposed: true, default: () => false});
			new Property(Cube, 'boolean', 'hitbox', {exposed: true, default: () => false});
			
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
			
			collision = new Toggle('toggle_collision', {
				icon: 'accessibility_new',
				condition: () => Cube.selected.length,
				onChange: function(value) // value is a boolean
				{
					Undo.initEdit({elements: Cube.selected});
					console.log(value);
					Cube.selected[0].collision = value;
					Undo.finishEdit('Change Collision');
				}
			});
			
			hitbox = new Toggle('toggle_hitbox', {
				icon: 'view_in_ar',
				condition: () => Cube.selected.length,
				onChange: function(value) // value is a boolean
				{
					Undo.initEdit({elements: Cube.selected});
					console.log(value);
					Cube.selected[0].hitbox = value;
					Undo.finishEdit('Change Hitbox');
				}
			});

			disableOtherCull = new Toggle('toggle_disable_other_cull', {
				icon: 'crop_square',
				condition: () => !Project.box_uv && Cube.selected.length,
				onChange: function(value) // value is a boolean
				{
					Undo.initEdit({elements: Cube.selected});
					Cube.selected[0].faces[main_uv.face].disable_other_cull = value;
					Undo.finishEdit('Change Disable Other Cull');
				}
			});
			
			Blockbench.on('update_selection', data => 
			{
				if (Format.id === 'free' && Cube.selected.length > 0)
				{
					layer.value = Cube.selected[0].faces[main_uv.face].layer;
					layer.set(Cube.selected[0].faces[main_uv.face].layer);

					collision.value = Cube.selected[0].collision;
					hitbox.value = Cube.selected[0].hitbox;
					disableOtherCull.value = Cube.selected[0].faces[main_uv.face].disableOtherCull;
					collision.updateEnabledState();
					hitbox.updateEnabledState();
					disableOtherCull.updateEnabledState();
				}
			});
			
			Toolbox.add(layer);
			Toolbox.add(collision);
			Toolbox.add(hitbox);
			Toolbox.add(disableOtherCull);
        },
		
        onunload()
		{
            layer.delete();
            collision.delete();
            hitbox.delete();
            disableOtherCull.delete();
        }
    });

})();