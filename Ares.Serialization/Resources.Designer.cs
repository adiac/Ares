﻿//------------------------------------------------------------------------------
// <auto-generated>
//     This code was generated by a tool.
//     Runtime Version:2.0.50727.1433
//
//     Changes to this file may cause incorrect behavior and will be lost if
//     the code is regenerated.
// </auto-generated>
//------------------------------------------------------------------------------

namespace Ares.Serialization
{
    using System;
    
    
    /// <summary>
    ///   A strongly-typed resource class, for looking up localized strings, etc.
    /// </summary>
    // This class was auto-generated by the StronglyTypedResourceBuilder
    // class via a tool like ResGen or Visual Studio.
    // To add or remove a member, edit your .ResX file then rerun ResGen
    // with the /str option, or rebuild your VS project.
    [global::System.CodeDom.Compiler.GeneratedCodeAttribute("System.Resources.Tools.StronglyTypedResourceBuilder", "2.0.0.0")]
    [global::System.Diagnostics.DebuggerNonUserCodeAttribute()]
    [global::System.Runtime.CompilerServices.CompilerGeneratedAttribute()]
    internal class Resources
    {
        
        private static global::System.Resources.ResourceManager resourceMan;
        
        private static global::System.Globalization.CultureInfo resourceCulture;
        
        [global::System.Diagnostics.CodeAnalysis.SuppressMessageAttribute("Microsoft.Performance", "CA1811:AvoidUncalledPrivateCode")]
        internal Resources()
        {
        }
        
        /// <summary>
        ///   Returns the cached ResourceManager instance used by this class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Resources.ResourceManager ResourceManager
        {
            get
            {
                if (object.ReferenceEquals(resourceMan, null))
                {
                    global::System.Resources.ResourceManager temp = new global::System.Resources.ResourceManager("Ares.Serialization.Resources", typeof(Resources).Assembly);
                    resourceMan = temp;
                }
                return resourceMan;
            }
        }
        
        /// <summary>
        ///   Overrides the current thread's CurrentUICulture property for all
        ///   resource lookups using this strongly typed resource class.
        /// </summary>
        [global::System.ComponentModel.EditorBrowsableAttribute(global::System.ComponentModel.EditorBrowsableState.Advanced)]
        internal static global::System.Globalization.CultureInfo Culture
        {
            get
            {
                return resourceCulture;
            }
            set
            {
                resourceCulture = value;
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Assembly {0} could not be loaded!.
        /// </summary>
        internal static string AssemblyNotFound
        {
            get
            {
                return ResourceManager.GetString("AssemblyNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Invalid array bounds!.
        /// </summary>
        internal static string InvalidArrayBounds
        {
            get
            {
                return ResourceManager.GetString("InvalidArrayBounds", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Member information is missing!.
        /// </summary>
        internal static string MemberInfoMissing
        {
            get
            {
                return ResourceManager.GetString("MemberInfoMissing", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Member type {0} unknown!.
        /// </summary>
        internal static string MemberTypeUnknown
        {
            get
            {
                return ResourceManager.GetString("MemberTypeUnknown", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Type {0} is not serializable!.
        /// </summary>
        internal static string NotSerializable
        {
            get
            {
                return ResourceManager.GetString("NotSerializable", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Duplicate TypeID found!.
        /// </summary>
        internal static string TypeIDDuplicate
        {
            get
            {
                return ResourceManager.GetString("TypeIDDuplicate", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Type information is missing!.
        /// </summary>
        internal static string TypeInfoMissing
        {
            get
            {
                return ResourceManager.GetString("TypeInfoMissing", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Type {0} could not be loaded!.
        /// </summary>
        internal static string TypeNotFound
        {
            get
            {
                return ResourceManager.GetString("TypeNotFound", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Type {0} not yet read!.
        /// </summary>
        internal static string TypeNotYetRead
        {
            get
            {
                return ResourceManager.GetString("TypeNotYetRead", resourceCulture);
            }
        }
        
        /// <summary>
        ///   Looks up a localized string similar to Defect XML format!.
        /// </summary>
        internal static string XmlDefect
        {
            get
            {
                return ResourceManager.GetString("XmlDefect", resourceCulture);
            }
        }
    }
}
