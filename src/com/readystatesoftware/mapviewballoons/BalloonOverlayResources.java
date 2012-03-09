/* Copyright (c) 2010-2012 Pierre LEVY androidsoft.org
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.readystatesoftware.mapviewballoons;

/**
 *
 * @author pierre
 */
public class BalloonOverlayResources
{
    private int mLayout;
    private int mItemTitleId;
    private int mItemSnippetId;
    private int mCloseId;

    /**
     * @return the layout
     */
    public int getLayout()
    {
        return mLayout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(int layout)
    {
        mLayout = layout;
    }

    /**
     * @return the itemTitleId
     */
    public int getItemTitleId()
    {
        return mItemTitleId;
    }

    /**
     * @param itemTitleId the itemTitleId to set
     */
    public void setItemTitleId(int itemTitleId)
    {
        mItemTitleId = itemTitleId;
    }

    /**
     * @return the itemSnippetId
     */
    public int getItemSnippetId()
    {
        return mItemSnippetId;
    }

    /**
     * @param itemSnippetId the itemSnippetId to set
     */
    public void setItemSnippetId(int itemSnippetId)
    {
        mItemSnippetId = itemSnippetId;
    }

    /**
     * @return the closeId
     */
    public int getCloseId()
    {
        return mCloseId;
    }

    /**
     * @param closeId the closeId to set
     */
    public void setCloseId(int closeId)
    {
        mCloseId = closeId;
    }
    
}
