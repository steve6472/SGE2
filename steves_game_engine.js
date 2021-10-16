(function() {
    var layer;
	
	var collision, hitbox, disableOtherCull/*, setId*/;
	var randomizeUV;

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
			new Property(CubeFace, 'string', 'layer', {exposed: true, default: () => "normal"});
			new Property(CubeFace, 'boolean', 'disable_other_cull', {exposed: true, default: () => false});
			new Property(Cube, 'boolean', 'collision', {exposed: true, default: () => false});
			new Property(Cube, 'boolean', 'hitbox', {exposed: true, default: () => false});
			//new Property(Cube, 'string', 'id', {exposed: true, default: () => ""});
			
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
					//Cube.selected[0].faces[UVEditor.vue.selected_faces[0]].layer = slider.value;
					Cube.selected.forEach(cube => {
                        cube.faces[UVEditor.vue.selected_faces[0]].layer = slider.value;
                    });
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
					//Cube.selected[0].collision = value;
					Cube.selected.forEach(cube => {
                        cube.collision = value;
                    });
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
					//Cube.selected[0].hitbox = value;
					Cube.selected.forEach(cube => {
                        cube.hitbox = value;
                    });
					Undo.finishEdit('Change Hitbox');
				}
			});

			disableOtherCull = new Toggle('toggle_disable_other_cull', {
				icon: 'crop_square',
				condition: () => !Project.box_uv && Cube.selected.length,
				onChange: function(value) // value is a boolean
				{
					Undo.initEdit({elements: Cube.selected});
					//Cube.selected[0].faces[UVEditor.vue.selected_faces[0]].disable_other_cull = value;
					Cube.selected.forEach(cube => {
                        cube.faces[UVEditor.vue.selected_faces[0]].disable_other_cull = disableOtherCull.value;
                    });
					Undo.finishEdit('Change Disable Other Cull');
				}
			});

			/*setId = new Action('set_id', {
                name: 'Set ID',
                description: 'Sets id',
                icon: 'fingerprint',
				condition: () => !Project.box_uv && Cube.selected.length,
                click: function() {
					new Dialog({
						id: 'set_id_dialog',
						title: 'ID',
						form: {
							ID: {label: 'Id', type: 'input', value: Cube.selected[0].id, placeholder: "Cube Id"}
						},
						onConfirm: function(formData) {
					
							Undo.initEdit({elements: Cube.selected});
							Cube.selected[0].id = formData.ID;
							Undo.finishEdit('randomize cube height');
						this.hide()
						}
					}).show()
                }
            });*/
 
			
			
            randomizeUV = new Action('randomize_uv',
			{
                name: 'Randomize UV',
                description: 'Randomizes position of UV',
                icon: 'open_with',
				condition: () => Format.id === 'free' && Cube.selected.length && !Project.box_uv,
                click: function() 
				{
                    Undo.initEdit({elements: Cube.selected});
					
					let newTint;
					let face = UVEditor.vue.selected_faces[0];
					
                    Cube.selected.forEach(cube =>
					{
						let w = cube.faces[face].uv[2] - cube.faces[face].uv[0];
						let h = cube.faces[face].uv[3] - cube.faces[face].uv[1];
						
						let x = Math.floor(Math.random() * (17 - w));
						let y = Math.floor(Math.random() * (17 - h));
						cube.faces[face].uv = [x, y, x + w, y + h];
						
						main_uv.message("Randomized UV for selected cubes");
						
						Canvas.updateUV(cube)
                    });
					
                    Undo.finishEdit('Randomized UV');
                }
            });
			
			
			
			Blockbench.on('update_selection', data => 
			{
				if (Format.id === 'free' && Cube.selected.length > 0)
				{
					if (UVEditor.vue.selected_faces[0] != undefined)
					{
						layer.value = Cube.selected[0].faces[UVEditor.vue.selected_faces[0]].layer;
						disableOtherCull.value = Cube.selected[0].faces[UVEditor.vue.selected_faces[0]].disableOtherCull;
					}
					//layer.set(Cube.selected[0].faces[UVEditor.vue.selected_faces[0]].layer);

					collision.value = Cube.selected[0].collision;
					hitbox.value = Cube.selected[0].hitbox;
					
					collision.updateEnabledState();
					hitbox.updateEnabledState();
					disableOtherCull.updateEnabledState();
				}
			});
			
			Toolbox.add(layer);
			Toolbox.add(collision);
			Toolbox.add(hitbox);
			Toolbox.add(disableOtherCull);
			//Toolbox.add(setId);
			MenuBar.addAction(randomizeUV, 'filter')
        },
		
        onunload()
		{
            layer.delete();
            collision.delete();
            hitbox.delete();
            disableOtherCull.delete();
			randomizeUV.delete();
        }
    });

})();