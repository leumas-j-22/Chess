# "make" to compile
# "make chess" to run

JAVAC = javac

JAVA = java

SRCDIR = src

OUTDIR = bin

JFLAGS = -d $(OUTDIR)

RM = rm

compile:
	@$(JAVAC) $(JFLAGS) $(SRCDIR)/chess/*.java $(SRCDIR)/chess/Pieces/*.java

chess:
	@$(JAVA) -cp $(OUTDIR) chess/Chess

clean:
	@$(RM) $(OUTDIR)/chess/*.class $(OUTDIR)/chess/Pieces/*.class