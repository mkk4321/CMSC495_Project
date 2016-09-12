/**
 *
 * @author Manoj
 */

package cmsc495_tt;
import java.awt.event.KeyEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.KeyStroke;
import javax.swing.table.TableModel;
import javax.swing.JTable;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultTreeCellEditor;

public class SkipColumnTable extends JTable {
 
    public static enum DIR {
        Up, Down, Left, Right
    }

    /**
     * The logger
     */
    private static final Logger LOG = Logger.getLogger(SkipColumnTable.class.getCanonicalName());

    public SkipColumnTable() {
        init();
    }

    /**
     * Default constructor, initialising the table with a TableModel
     *
     * @param tableModel
     */
    public SkipColumnTable(TableModel tableModel) {
        super(tableModel);
        init();
    }

    
    private void init() {
        // When pressing enter, move to the next cell instead of the next row.
        getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
                .put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0), "selectNextColumnCell");

    }

    @Override
    public void changeSelection(int row, int col, boolean toggle, boolean expand) {
        // This method is called when the user tries to move to a different cell.
        // If the cell they're trying to move to is not editable, we look for
        // the next cell in the proper direction that is editable.
        
        // initialise the new (future) selection to the indices as specified in
        // the method call.
        // if everything is "normal" we can just use these values. All special
        // cases will overwrite the value contained herein. Eventually, these
        // variables are returned.
        int targetViewRow = row;
        int targetViewCol = col;

        int targetModelRow = convertRowIndexToModel(targetViewRow);
        int targetModelCol = convertColumnIndexToModel(targetViewCol);

     

        if (!getModel().isCellEditable(targetModelRow, targetModelCol)) {
            if (LOG.isLoggable(Level.FINE)){
                LOG.fine(String.format("Cell %d,%d (%d,%d) is not editable!",
                        targetViewRow, targetViewCol, targetModelRow, targetModelCol));
            }

            
            int oldViewRow = getEditingRow();
            int oldViewCol = getEditingColumn();
            if (oldViewRow == -1) {
                oldViewRow = getSelectedRow();
            }
            if (oldViewCol == -1) {
                oldViewCol = getSelectedColumn();
            }

            if (LOG.isLoggable(Level.FINE)) {
                LOG.fine(String.format("We came from cell %d,%d",
                        oldViewRow, oldViewCol));
            }

            DIR direction;
            if (oldViewCol == targetViewCol && oldViewRow < targetViewRow) {
                direction = DIR.Down;
            } else if (oldViewCol == targetViewCol && oldViewRow >= targetViewRow) {
                direction = DIR.Up;
            } else if (oldViewCol == targetViewCol && oldViewRow == targetViewRow) {
                direction = DIR.Left;
            } else {
                // defaulting to right
                direction = DIR.Right;
            }

            LOG.fine(String.format("Moving %s", direction));

            // determine next cell position
            while (!getModel().isCellEditable(targetModelRow, targetModelCol)) {
                LOG.fine(String.format("Model-Cell %d,%d is still not editable",
                        targetModelRow, targetModelCol));
                switch (direction) {
                    case Up:
                        targetViewRow -= 1;
                        if (targetViewRow < 0) {
                            targetViewRow = getRowCount() - 1;
                        }
                        break;
                    case Down:
                        targetViewRow += 1;
                        if (targetViewRow > getRowCount() - 1) {
                            targetViewRow = 0;
                        }
                        break;
                    case Left:
                        targetViewCol -= 1;
                        if (targetViewCol < 0) {
                            targetViewCol = getRowCount() - 1;
                            targetViewRow -= 1;
                            if (targetViewRow < 0) {
                                targetViewRow = getRowCount() - 1;
                            }
                        }
                        break;
                    case Right:
                        targetViewCol += 1;
                        if (targetViewCol > getColumnCount() - 1) {
                            targetViewCol = 0;
                            targetViewRow += 1;
                            if (targetViewRow > getRowCount() - 1) {
                                targetViewRow = 0;
                            }
                        }
                        break;
                }
                targetModelRow = convertRowIndexToModel(targetViewRow);
                targetModelCol = convertColumnIndexToModel(targetViewCol);
            }
            LOG.fine(String.format("Trying to move selection to %d,%d instead!",
                    targetViewRow, targetViewCol));

        }

        super.changeSelection(targetViewRow, targetViewCol, toggle, expand);
        // this.getDefaultEditor (String.class).addCellEditorListener(UpcChange);
    }
        
}
