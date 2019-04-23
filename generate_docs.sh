# generate API docs for sample-project
for x in sample-project/generated/swagger/*.yaml; do 
	redoc-cli bundle $x;
	t=$(echo $x | sed 's/\.yaml$/.html/'); 
	t=$(echo $t | sed 's@.*/@@'); 
	mv redoc-static.html $t;
done