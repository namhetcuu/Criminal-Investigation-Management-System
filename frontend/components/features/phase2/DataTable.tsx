/**
 * DATA TABLE COMPONENT
 * 
 * A reusable table component that displays data in a structured format.
 * Supports custom column configurations, custom cell rendering, and action buttons.
 * 
 * USAGE:
 * <DataTable
 *   columns={[
 *     { key: 'name', label: 'Full Name' },
 *     { key: 'email', label: 'Email', render: (value) => <a href={`mailto:${value}`}>{value}</a> }
 *   ]}
 *   data={[
 *     { name: 'John Doe', email: 'john@example.com' },
 *     { name: 'Jane Smith', email: 'jane@example.com' }
 *   ]}
 *   actions={(row, index) => <ActionButtons row={row} index={index} onEdit={handleEdit} />}
 * />
 * 
 * FEATURES:
 * - Flexible column configuration with custom rendering
 * - Optional actions column for buttons/links
 * - Consistent styling with rounded borders and gray background
 * - Responsive design with proper spacing
 */

// Column configuration interface
interface Column {
  key: string;      // Property name in the data object
  label: string;    // Display name for column header
  render?: (value: any, row: any, index: number) => React.ReactNode; // Optional custom renderer
}

// Main component props interface
interface DataTableProps {
  columns: Column[];  // Array of column configurations
  data: any[];       // Array of data objects to display
  actions?: (row: any, index: number) => React.ReactNode; // Optional actions column renderer
}

export function DataTable({ columns, data, actions }: DataTableProps) {
  return (
    // Table container with rounded borders and gray background
    <div className="bg-gray-50 rounded-lg border border-gray-300 overflow-hidden">
      <table className="w-full text-sm">
        
        {/* TABLE HEADER */}
        <thead>
          <tr className="bg-gray-100">
            {/* Render column headers */}
            {columns.map((col) => (
              <th key={col.key} className="p-3 text-left font-medium">
                {col.label}
              </th>
            ))}
            {/* Add empty header for actions column if actions are provided */}
            {actions && <th className="p-3"></th>}
          </tr>
        </thead>
        
        {/* TABLE BODY */}
        <tbody>
          {/* Render each data row */}
          {data.map((row, i) => (
            <tr key={i} className="border-t border-gray-200">
              
              {/* Render each column cell */}
              {columns.map((col) => (
                <td key={col.key} className="p-3">
                  {/* Use custom render function if provided, otherwise display raw value */}
                  {col.render ? col.render(row[col.key], row, i) : row[col.key]}
                </td>
              ))}
              
              {/* Actions column */}
              {actions && (
                <td className="p-3">
                  {/* Flex container to align action buttons to the right */}
                  <div className="flex gap-1 justify-end">
                    {/* Render the actions component with row data and index */}
                    {actions(row, i)}
                  </div>
                </td>
              )}
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}